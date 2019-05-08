package com.ithellan.jamb.service;

import java.util.Optional;
import java.util.UUID;

import com.ithellam.jamb.api.dto.ContainerMutationDto;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto;
import com.ithellam.jamb.api.siren.ContainerSirenDto;

public interface IContainerService {
    ContainerCollectionSirenDto getContainers();

    ContainerSirenDto createContainer(final ContainerMutationDto mutation);

    Optional<ContainerSirenDto> getContainer(final String name);

    Optional<ContainerSirenDto> getContainer(final UUID id);
}
