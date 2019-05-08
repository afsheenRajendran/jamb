package com.ithellam.jamb.api;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ithellam.jamb.api.dto.ContainerMutationDto;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto;
import com.ithellam.jamb.api.siren.ContainerSirenDto;
import com.ithellan.jamb.service.IContainerService;

public class ContainerResource extends AbstractResource implements IContainerResource {
    private static final Logger logger = LoggerFactory.getLogger(ContainerResource.class);

    @Inject
    private IContainerService containerService;

    @Override
    public Response createContainer(final ContainerMutationDto mutation) {
        try {
            logger.debug("createContainer started");
            final ContainerSirenDto entity = containerService.createContainer(mutation);

            return Response.ok(entity).build();

        } catch (final Exception ex) {
            return Response.serverError().build();
        }
    }

    @Override
    public Response getContainers() {
        logger.debug("getContainers started");

        final ContainerCollectionSirenDto containerCollection = containerService.getContainers();

        final List<ContainerSirenDto> containers = containerCollection.getItems();
        logger.info("getContainers found {} containers", containers.size());

        return Response.ok(containerCollection).build();
    }

    @Override
    public Response getContainer(final String name) {
        return null;
    }

    @Override
    public Response getContainer(final UUID containerId) {
        return containerService.getContainer(containerId)
        .map(this::ok)
        .orElseGet(this::notFound);
    }
}
