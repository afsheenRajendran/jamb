package com.ithellam.jamb.rest.client;

import java.util.UUID;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.ithellam.jamb.api.IProductResource;
import com.ithellam.jamb.api.dto.ProductMutationDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto;
import com.ithellam.jamb.api.siren.ProductSirenDto;
import com.ithellam.jamb.rest.ClientResource;
import com.ithellam.jamb.rest.IResult;

public class ProductClientResource extends ClientResource implements IProductClientResource {
    private final IProductResource productResource;

    public ProductClientResource(final IProductResource productResource, final ListeningExecutorService executorService) {
        super(executorService);

        this.productResource = productResource;
    }

    @Override
    public ListenableFuture<IResult<ProductCollectionSirenDto>> getProducts() {
        return execute(() -> productResource.getProducts(), ProductCollectionSirenDto.class);
    }

    @Override
    public ListenableFuture<IResult<ProductSirenDto>> createProduct(final ProductMutationDto mutation) {
        return execute(() -> productResource.createProduct(mutation), ProductSirenDto.class);
    }

    @Override
    public ListenableFuture<IResult<ProductSirenDto>> getProduct(final UUID productId) {
        return execute(() -> productResource.getProduct(productId), ProductSirenDto.class);
    }
}
