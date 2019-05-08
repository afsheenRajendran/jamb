package com.ithellan.jamb.service;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ithellam.jamb.api.dto.ContainerMutationDto;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto;
import com.ithellam.jamb.api.siren.ContainerSirenDto;
import com.ithellan.jamb.domain.Container;
import com.ithellan.jamb.persistence.IContainerPersistenceService;

public class ContainerService implements IContainerService{
    private static final Logger logger = LoggerFactory.getLogger(ContainerService.class);

    @Inject
    private IContainerPersistenceService persistence;

    @Override
    public ContainerCollectionSirenDto getContainers() {
        return ServiceDtoMapper.createContainerCollectionSirenDto(persistence.getAll());
    }

    @Override
    public ContainerSirenDto createContainer(final ContainerMutationDto mutation) {
        final UUID newContainerId = UUID.randomUUID();
        logger.debug("Starting to create new container with id: {}", newContainerId);

        final Container containerToSave = Container.builder()
            .withName(mutation.getName())
            .withColor(mutation.getColor())
            .withUniqueId(newContainerId)
            .build();

        return ServiceDtoMapper.createContainerSirenDto(persistence.save(containerToSave));
    }

    @Override
    public Optional<ContainerSirenDto> getContainer(final String name) {
        return persistence.get(name).map(ServiceDtoMapper::createContainerSirenDto);
    }

    @Override
    public Optional<ContainerSirenDto> getContainer(final UUID id) {
        return persistence.get(id).map(ServiceDtoMapper::createContainerSirenDto);
    }
}
