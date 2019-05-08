package com.ithellam.jamb.tests;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ListenableFuture;
import com.ithellam.jamb.api.dto.ContainerMutationDto;
import com.ithellam.jamb.api.dto.ProductMutationDto;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto;
import com.ithellam.jamb.api.siren.ContainerSirenDto;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto;
import com.ithellam.jamb.api.siren.ProductSirenDto;
import com.ithellam.jamb.rest.IResult;
import com.ithellam.jamb.rest.client.IContainerClientResource;
import com.ithellam.jamb.rest.client.IMainServiceClient;
import com.ithellam.jamb.rest.client.IProductClientResource;

public class Step100 {
    private static final Logger logger = LoggerFactory.getLogger(Step100.class);

    private final IMainServiceClient mainServiceClient;

    private final IContainerClientResource containerClientResource;

    private final IProductClientResource productClientResource;

    public Step100() {
        mainServiceClient = new SimpleServiceClientFactory().getMainServiceClient();

        containerClientResource = mainServiceClient.getContainerResource();

        productClientResource = mainServiceClient.getProductResource();
    }

    public void execute() throws Exception {
        checkContainers();
    }

    private void checkContainers() throws Exception {
        final ContainerMutationDto cto1 = buildContainer("big box", "grey");
        final ContainerMutationDto cto2 = buildContainer("med box", "blue");

        logger.info("Before creating C1");
        final ContainerSirenDto container1 = createContainer(cto1);
        final UUID container1Id = container1.getProperties().getId();
        logger.info("After creating C1 : {}", container1Id);

        logger.info("Before creating C2");
        final ContainerSirenDto container2 = createContainer(cto2);
        final UUID container2Id = container2.getProperties().getId();
        logger.info("After creating C2 : {}", container2Id);

        logger.info("Before calling getContainers");
        final ContainerCollectionSirenDto collection = SirenUtils.getSirenDto(containerClientResource.getContainers());

        collection.getItems().forEach(container -> {
            logger.info("Containers collection has container with id: {}", container.getProperties().getId());
        });

        final ContainerSirenDto qto2 = SirenUtils.getSirenDto(containerClientResource.getContainer(container2Id));
        logger.info("Found container with id: {}", qto2.getProperties().getId());

        try {
            SirenUtils.getSirenDto(containerClientResource.getContainer(UUID.randomUUID()));

        } catch (final RuntimeException ex) {
            logger.info("Attempt to retrieve container with random id fails as expected.", ex);
        }

        checkProducts(container1.getProperties().getId());
    }

    private void checkProducts(final UUID containerId) throws Exception {
        final ProductMutationDto dto1 = buildProduct(containerId,"P1","azure");
        final ProductMutationDto dto2 = buildProduct(containerId,"P2","creole");

        logger.info("Before creating P1");
        final ProductSirenDto product1 = createProduct(dto1);
        final UUID product1Id = product1.getProperties().getId();
        logger.info("After creating P1 : {}", product1Id);

        logger.info("Before creating P2");
        final ProductSirenDto product2 = createProduct(dto2);
        logger.info("After creating P2 : {}", product2.getProperties().getId());

        logger.info("Before calling getProducts");

        final ProductCollectionSirenDto collection = SirenUtils.getSirenDto(productClientResource.getProducts());

        collection.getItems().forEach(product -> {
            logger.info("Products collection has product with id: {}", product.getProperties().getId());
        });

        logger.info("After calling getProducts");

        final ProductSirenDto qto1 = SirenUtils.getSirenDto(productClientResource.getProduct(product1Id));
        logger.info("Found product with id: {}", qto1.getProperties().getId());

        try {
            SirenUtils.getSirenDto(productClientResource.getProduct(UUID.randomUUID()));

        } catch (final RuntimeException ex) {
            logger.info("Attempt to retrieve container with random id fails as expected.", ex);
        }
    }

    private ContainerMutationDto buildContainer(final String name, final String color) {
        return ContainerMutationDto.builder()
            .withName(name)
            .withColor(color)
            .build();
    }

    private ProductMutationDto buildProduct(final UUID containerId, final String name, final String color) {
        return ProductMutationDto.builder()
            .withContainerId(containerId)
            .withName(name)
            .withColor(color)
            .build();
    }

    private ContainerSirenDto createContainer(final ContainerMutationDto dto) throws Exception {
        final ListenableFuture<IResult<ContainerSirenDto>> result = containerClientResource.createContainer(dto);

        return SirenUtils.getSirenDto(result);
    }

    private ProductSirenDto createProduct(final ProductMutationDto dto) throws Exception {
        return SirenUtils.getSirenDto(productClientResource.createProduct(dto));
    }
}


