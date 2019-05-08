package com.ithellan.jamb.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.ithellam.common.siren.SirenEntityBase;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto.ContainerCollectionSirenDtoProperties;
import com.ithellam.jamb.api.siren.ContainerSirenDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto.ProductCollectionSirenDtoProperties;
import com.ithellam.jamb.api.siren.ProductSirenDto;
import com.ithellan.jamb.domain.Container;
import com.ithellan.jamb.domain.Product;

public class ServiceDtoMapper {
    private ServiceDtoMapper() {
    }

    public static ContainerSirenDto createContainerSirenDto(final Container container) {
        return ContainerSirenDto.builder()
            .withId(container.getId())
            .withName(container.getName())
            .withColor(container.getColor())
            .build();
    }

    public static ContainerCollectionSirenDto createContainerCollectionSirenDto(final ImmutableList<Container> containers) {
        final List<SirenEntityBase> items = containers.stream()
            .map(container -> createContainerSirenDto(container))
            .map(dto -> dto.withRel("item"))
            .collect(Collectors.toList());

        final ContainerCollectionSirenDtoProperties properties = new ContainerCollectionSirenDtoProperties(
            UUID.randomUUID());

        return (ContainerCollectionSirenDto)new ContainerCollectionSirenDto()
            .withProperties(properties)
            .withEntities(items);
    }

    public static ProductSirenDto createProductSirenDto(final Product product) {
        return ProductSirenDto.builder()
            .withId(product.getId())
            .withContainerId(product.getContainerId())
            .withName(product.getName())
            .withColor(product.getColor())
            .build();
    }

    public static ProductCollectionSirenDto createProductCollectionSirenDto(final ImmutableList<Product> Products) {
        final List<SirenEntityBase> items = Products.stream()
            .map(Product -> createProductSirenDto(Product))
            .map(dto -> dto.withRel("item"))
            .collect(Collectors.toList());

        final ProductCollectionSirenDtoProperties properties = new ProductCollectionSirenDtoProperties(
            UUID.randomUUID());

        return (ProductCollectionSirenDto)new ProductCollectionSirenDto()
            .withProperties(properties)
            .withEntities(items);
    }
}
