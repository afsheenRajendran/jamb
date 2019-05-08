package com.ithellam.jamb.rest.client;

import java.util.UUID;

import com.google.common.util.concurrent.ListenableFuture;
import com.ithellam.jamb.api.IContainerResource;
import com.ithellam.jamb.api.dto.ContainerMutationDto;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto;
import com.ithellam.jamb.api.siren.ContainerSirenDto;
import com.ithellam.jamb.rest.IResult;
import com.ithellam.jamb.rest.ServiceInterface;


@ServiceInterface(IContainerResource.class)
public interface IContainerClientResource {
    ListenableFuture<IResult<ContainerCollectionSirenDto>> getContainers();

    ListenableFuture<IResult<ContainerSirenDto>> createContainer(final ContainerMutationDto mutation);

    ListenableFuture<IResult<ContainerSirenDto>> getContainer(final UUID containerId);
}
