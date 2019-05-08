package com.ithellan.jamb.domain;

import java.util.UUID;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.ithellam.common.utils.DeserializationConstructor;

public class Container extends AbstractPersistedObject<UUID> {
    private final UUID id;
    private final String name;
    private final String color;
    private final EContainerCategory category;

    @DeserializationConstructor
    private Container() {
        this(null, null, null, null, null);
    }

    public Container(final Long version, final UUID id, final String name, final String color, final EContainerCategory category) {
        super(version);
        this.id = id;
        this.name = name;
        this.color = color;
        this.category = category;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final Container record) {
        return (Builder) new Builder().fromRecord(record);
    }

    @Override
    public UUID getUniqueId() {
        return id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public EContainerCategory getCategory() {
        return category;
    }

    @Override
    protected ToStringHelper getToStringHelper() {
        return super.getToStringHelper()
        .add("name", name)
        .add("color", color);
    }

    public static class Builder extends AbstractPersistedObjectBuilder<Container, UUID> {
        private String name;
        private String color;
        private EContainerCategory category;

        private Builder() {
        }

        @Override
        public Container build() {
            return new Container(getVersion(), getUniqueId(), name, color, category);
        }


        @Override
        protected void onFromRecord(final Container record) {
            withName(record.getName()).withColor(record.getColor());
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withColor(final String color) {
            this.color = color;
            return this;
        }

        public Builder withCategory(final EContainerCategory category) {
            this.category = category;
            return this;
        }
    }
}

