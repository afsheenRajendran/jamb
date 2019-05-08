package com.ithellam.jamb.rest.client;

import java.util.UUID;

import com.google.common.util.concurrent.ListenableFuture;
import com.ithellam.jamb.api.IProductResource;
import com.ithellam.jamb.api.dto.ProductMutationDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto;
import com.ithellam.jamb.api.siren.ProductSirenDto;
import com.ithellam.jamb.rest.IResult;
import com.ithellam.jamb.rest.ServiceInterface;

@ServiceInterface(IProductResource.class)
public interface IProductClientResource {
    ListenableFuture<IResult<ProductCollectionSirenDto>> getProducts();

    ListenableFuture<IResult<ProductSirenDto>> createProduct(final ProductMutationDto mutation);

    ListenableFuture<IResult<ProductSirenDto>> getProduct(final UUID productId);
}
