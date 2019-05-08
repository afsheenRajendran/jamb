package com.ithellam.jamb.api.dto;

import javax.validation.constraints.NotNull;

import com.ithellam.common.utils.DeserializationConstructor;
import com.ithellam.common.utils.IBuilder;

public class ContainerMutationDto {
    @NotNull(message = "ContainerMutationDto.name cannot be null.")
    private final String name;
    private final String color;

    @DeserializationConstructor
    private ContainerMutationDto() {
        this(null,  null);
    }

    public ContainerMutationDto(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final ContainerMutationDto record) {
        return new Builder().fromRecord(record);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public static class Builder implements IBuilder<ContainerMutationDto> {
        private String name;
        private String color;

        private Builder() {
        }

        @Override
        public ContainerMutationDto build() {
            return new ContainerMutationDto(name, color);
        }

        @Override
        public Builder fromRecord(final ContainerMutationDto record) {
            return withName(record.getName())
            .withColor(record.getColor());
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
