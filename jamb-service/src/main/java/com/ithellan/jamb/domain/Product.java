package com.ithellan.jamb.domain;

import java.util.UUID;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.ithellam.common.utils.DeserializationConstructor;

public class Product extends AbstractPersistedObject<UUID> {
    private final UUID id;
    private final UUID containerId;
    private final String name;
    private final String color;
    private final EProductCategory category;

    @DeserializationConstructor
    private Product() {
        this(null, null, null, null, null, null);
    }

    public Product(final Long version, final UUID id, final UUID containerId,
    final String name, final String color, final EProductCategory category) {
        super(version);
        this.id = id;
        this.containerId = containerId;
        this.name = name;
        this.color = color;
        this.category = category;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final Product record) {
        return (Builder) new Builder().fromRecord(record);
    }

    @Override
    public UUID getUniqueId() {
        return id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getContainerId() {
        return containerId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public EProductCategory getCategory() {
        return category;
    }

    @Override
    protected ToStringHelper getToStringHelper() {
        return super.getToStringHelper()
        .add("containerId", containerId)
        .add("name", name)
        .add("color", color)
        .add("category", category);
    }

    public static class Builder extends AbstractPersistedObjectBuilder<Product, UUID> {
        private String name;
        private String color;
        private UUID containerId;
        private EProductCategory category;

        private Builder() {
        }

        @Override
        public Product build() {
            return new Product(getVersion(), getUniqueId(), containerId, name, color, category);
        }

        @Override
        protected void onFromRecord(final Product record) {
            withName(record.getName())
            .withColor(record.getColor())
            .withCategory(record.getCategory());
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withColor(final String color) {
            this.color = color;
            return this;
        }

        public Builder withContainerId(final UUID containerId) {
            this.containerId = containerId;
            return this;
        }

        public Builder withCategory(final EProductCategory category) {
            this.category = category;
            return this;
        }
    }
}

