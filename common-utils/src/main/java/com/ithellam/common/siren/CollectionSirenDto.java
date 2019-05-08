package com.ithellam.common.siren;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CollectionSirenDto<TItem extends SirenEntityBase, TProperties extends SirenEntityPropertiesBase> extends SirenEntity<TProperties> {
    public CollectionSirenDto<TItem, TProperties> withItem(final TItem item) {
        withEntity(item.withRel("item"));
        return this;
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public List<TItem> getItems() {
        return (List<TItem>) getEntities()
        .stream()
        .filter(e -> e.getRel().contains("item"))
        .collect(Collectors.toList());
    }

    public CollectionSirenDto<TItem, TProperties> withItems(final Collection<TItem> items) {
        items.forEach(this::withItem);
        return this;
    }
}
