package com.ithellam.jamb.api.siren;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import com.ithellam.common.siren.SirenEntity;
import com.ithellam.common.siren.SirenEntityPropertiesBase;
import com.ithellam.common.utils.DeserializationConstructor;
import com.ithellam.common.utils.IBuilder;
import com.ithellam.jamb.api.siren.ContainerSirenDto.ContainerSirenProperties;

public class ContainerSirenDto extends SirenEntity<ContainerSirenProperties> {
    public static Builder builder() {
        return new Builder();
    }

    public static class ContainerSirenProperties extends SirenEntityPropertiesBase {
        @XmlElement
        private final String name;

        @XmlElement
        private final String color;

        @DeserializationConstructor
        private ContainerSirenProperties() {
            this(null, null, null);
        }

        public ContainerSirenProperties(final UUID id, final String name, final String color) {
            super(id);
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }
    }

    public static class Builder implements IBuilder<ContainerSirenDto> {
        private UUID id;
        private String name;
        private String color;

        private Builder() {
        }

        @Override
        public ContainerSirenDto build() {
            final ContainerSirenProperties props = new ContainerSirenProperties(id, name, color);

            return (ContainerSirenDto) new ContainerSirenDto().withProperties(props);
        }

        @Override
        public IBuilder<ContainerSirenDto> fromRecord(final ContainerSirenDto record) {
            final ContainerSirenProperties props = record.getProperties();

            return withId(props.getId()).withName(props.getName()).withColor(props.getColor());
        }

        public Builder withId(final UUID id) {
            this.id = id;
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
