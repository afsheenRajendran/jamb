package com.ithellam.jamb.api.dto;

import java.util.UUID;
import com.ithellam.common.utils.DeserializationConstructor;
import com.ithellam.common.utils.IBuilder;
import javax.validation.constraints.NotNull;

public class ProductMutationDto {
    @NotNull(message = "ProductMutationDto.containerId cannot be null.")
    private final UUID containerId;
    @NotNull(message = "ProductMutationDto.name cannot be null.")
    private final String name;
    private final String color;

    @DeserializationConstructor
    private ProductMutationDto() {
        this(null, null, null);
    }

    public ProductMutationDto(final UUID containerId, final String name, final String color) {
        this.containerId = containerId;
        this.name = name;
        this.color = color;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final ProductMutationDto record) {
        return new Builder().fromRecord(record);
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

    public static class Builder implements IBuilder<ProductMutationDto> {
        private UUID containerId;
        private String name;
        private String color;

        private Builder() {
        }

        @Override
        public ProductMutationDto build() {
            return new ProductMutationDto(containerId, name, color);
        }

        @Override
        public Builder fromRecord(final ProductMutationDto record) {
            return withContainerId(record.getContainerId())
            .withName(record.getName())
            .withColor(record.getColor());
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
