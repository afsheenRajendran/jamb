package com.ithellan.jamb.service;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ithellam.jamb.api.dto.ProductMutationDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto;
import com.ithellam.jamb.api.siren.ProductSirenDto;
import com.ithellan.jamb.domain.Product;
import com.ithellan.jamb.persistence.IProductPersistenceService;

public class ProductService implements IProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Inject
    private IProductPersistenceService persistence;

    @Override
    public ProductCollectionSirenDto getProducts() {
        return  ServiceDtoMapper.createProductCollectionSirenDto(persistence.getAll());
    }

    @Override
    public ProductSirenDto createProduct(final ProductMutationDto mutation) {
        final UUID newProductId = UUID.randomUUID();
        logger.debug("Starting to create new product with id: {}", newProductId);

        final Product productToSave = Product.builder()
            .withContainerId(mutation.getContainerId())
            .withName(mutation.getName())
            .withColor(mutation.getColor())
            .withUniqueId(newProductId)
            .build();

        return ServiceDtoMapper.createProductSirenDto(persistence.save(productToSave));
    }

    @Override
    public Optional<ProductSirenDto> getProduct(final String name) {
        return persistence.get(name).map(ServiceDtoMapper::createProductSirenDto);
    }

    @Override
    public Optional<ProductSirenDto> getProduct(final UUID id) {
        return persistence.get(id).map(ServiceDtoMapper::createProductSirenDto);
    }
}
