package com.ithellam.jamb.api.siren;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import com.ithellam.common.siren.SirenEntity;
import com.ithellam.common.siren.SirenEntityPropertiesBase;
import com.ithellam.common.utils.DeserializationConstructor;
import com.ithellam.common.utils.IBuilder;

import com.ithellam.jamb.api.siren.ProductSirenDto.ProductSirenProperties;


public class ProductSirenDto extends SirenEntity<ProductSirenProperties> {
    public static Builder builder() {
        return new Builder();
    }

    public static class ProductSirenProperties extends SirenEntityPropertiesBase {
        @XmlElement
        private final UUID containerId;

        @XmlElement
        private final String name;

        @XmlElement
        private final String color;

        @DeserializationConstructor
        private ProductSirenProperties() {
            this(null, null, null, null);
        }

        public ProductSirenProperties(final UUID id, final UUID containerId, final String name, final String color) {
            super(id);
            this.containerId = containerId;
            this.name = name;
            this.color = color;
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
    }

    public static class Builder implements IBuilder<ProductSirenDto> {
        private UUID id;
        private UUID containerId;
        private String name;
        private String color;

        private Builder() {
        }

        @Override
        public ProductSirenDto build() {
            final ProductSirenProperties props = new ProductSirenProperties(id, containerId, name, color);

            return (ProductSirenDto) new ProductSirenDto().withProperties(props);
        }

        @Override
        public IBuilder<ProductSirenDto> fromRecord(final ProductSirenDto record) {
            final ProductSirenProperties props = record.getProperties();

            return withId(props.getId())
            .withContainerId(props.getContainerId())
            .withName(props.getName())
            .withColor(props.getColor());
        }

        public Builder withId(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder withContainerId(final UUID containerId) {
            this.containerId = containerId;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withColor(final String color) {
            this.color = color;
            return this;
        }

    }
}
