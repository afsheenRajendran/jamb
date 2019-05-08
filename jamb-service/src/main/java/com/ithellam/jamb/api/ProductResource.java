package com.ithellam.jamb.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ithellam.jamb.api.dto.ProductMutationDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto;
import com.ithellam.jamb.api.siren.ProductSirenDto;
import com.ithellan.jamb.service.IProductService;

public class ProductResource extends AbstractResource implements IProductResource {
    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private IProductService productService;

    @Override
    public Response createProduct(final ProductMutationDto mutation) {
        try {
            final ProductSirenDto productSirenDto = productService.createProduct(mutation);

            return Response.ok(productSirenDto).build();

        } catch (final MyValidationException ex){
            logger.error("Encountered exception for given ProductMutationDto.", ex);
            return Response.status(404).build();

        } catch (final Exception ex) {
            return Response.serverError().build();
        }
    }

    @Override
    public Response getProducts() {
        logger.info("getProducts started");

        final ProductCollectionSirenDto productCollection = productService.getProducts();

        final List<ProductSirenDto> products = productCollection.getItems();

        logger.info("getProducts found {} products", products.size());

        return Response.ok(productCollection).build();
    }

    @Override
    public Response getProduct(final UUID productId) {
        logger.info("getProduct started for given productId: {}", productId);

        final Optional<ProductSirenDto> optProductSirenDto = productService.getProduct(productId);

        if (!optProductSirenDto.isPresent()) {
            logger.info("Unable to find product with id: {}", productId);
            return this.notFound();
        }

        final ProductSirenDto productSirenDto = optProductSirenDto.get();

        logger.info("getProduct found product with name: {} and color: {}",
            productSirenDto.getProperties().getName(), productSirenDto.getProperties().getColor());

        return Response.ok(productSirenDto).build();
    }
}
