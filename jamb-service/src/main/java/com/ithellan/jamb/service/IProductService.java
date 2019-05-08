package com.ithellan.jamb.service;

import java.util.Optional;
import java.util.UUID;

import com.ithellam.jamb.api.dto.ProductMutationDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto;
import com.ithellam.jamb.api.siren.ProductSirenDto;

public interface IProductService {
    ProductCollectionSirenDto getProducts();

    ProductSirenDto createProduct(final ProductMutationDto mutation);

    Optional<ProductSirenDto> getProduct(final String name);

    Optional<ProductSirenDto> getProduct(final UUID id);
}
