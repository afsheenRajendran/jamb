package com.ithellam.jamb.rest.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.ithellam.jamb.api.IContainerResource;
import com.ithellam.jamb.api.dto.ContainerMutationDto;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto;
import com.ithellam.jamb.api.siren.ContainerSirenDto;
import com.ithellam.jamb.rest.ClientResource;
import com.ithellam.jamb.rest.IResult;

public class ContainerClientResource extends ClientResource implements IContainerClientResource {
    private static final Logger logger = LoggerFactory.getLogger(ContainerClientResource.class);

    private final IContainerResource containerResource;

    public ContainerClientResource(final IContainerResource containerResource,
            final ListeningExecutorService executorService) {
        super(executorService);

        this.containerResource = containerResource;
    }

    @Override
    public ListenableFuture<IResult<ContainerCollectionSirenDto>> getContainers() {
        logger.info("ccr 030 getContainers");
        return execute(() -> containerResource.getContainers(), ContainerCollectionSirenDto.class);
    }

    @Override
    public ListenableFuture<IResult<ContainerSirenDto>> createContainer(final ContainerMutationDto mutation) {
        logger.info("ccr 036 createContainer");
        return execute(() -> containerResource.createContainer(mutation), ContainerSirenDto.class);
    }

    public ListenableFuture<IResult<ContainerSirenDto>> getContainer(final UUID containerId) {
        return execute(() -> containerResource.getContainer(containerId), ContainerSirenDto.class);
    }

}
