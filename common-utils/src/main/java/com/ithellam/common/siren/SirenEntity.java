package com.ithellam.common.siren;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public abstract class SirenEntity<T extends SirenEntityPropertiesBase> extends SirenEntityBase {
    @XmlElement
    private List<String> actions = new ArrayList<>();

    @XmlElement
    private List<SirenEntityBase> entities = new ArrayList<>();

    @XmlElement
    private final List<SirenLinkedEntity> links = new ArrayList<>();

    @XmlElement
    private T properties;

    protected SirenEntity() {
    }

    protected SirenEntity(final SirenEntity<T> template) {
        this.actions = new ArrayList<>(template.actions);
        this.entities.addAll(template.entities);
        this.links.addAll(template.links);
        this.properties = template.properties;
    }

    public T getProperties() {
        return properties;
    }

    public List<String> getActions() {
        return actions;
    }

    public List<SirenLinkedEntity> getLinks() {
        return links;
    }

    public List<? extends SirenEntityBase> getEntities() {
        return entities;
    }

    // TODO: Change any List<T> to use Collection<T>
    public SirenEntity<T> withEntities(final List<SirenEntityBase> entities) {
        this.entities = entities;
        return this;
    }

    public SirenEntity<T> withEntity(final SirenEntityBase entity) {
        this.entities.add(entity);
        return this;
    }

    public SirenEntity<T> withProperties(final T properties) {
        this.properties = properties;
        return this;
    }

    public SirenEntity<T> withLink(final SirenLinkedEntity link) {
        this.links.add(link);
        return this;
    }

    public SirenEntity<T> withActions(final List<String> actions) {
        this.actions = actions;
        return this;
    }

    public SirenEntity<T> clearLinks() {
        this.links.clear();
        return this;
    }
}
