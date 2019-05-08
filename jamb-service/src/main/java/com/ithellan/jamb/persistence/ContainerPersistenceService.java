package com.ithellan.jamb.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ithellan.jamb.domain.Container;

@Startup
@Singleton
public class ContainerPersistenceService implements IContainerPersistenceService {
    private static final Logger logger = LoggerFactory.getLogger(ContainerPersistenceService.class);

    private final Map<UUID, List<Container>> objectMap = new ConcurrentHashMap<>();

    final AtomicLong versionNumberGenerator = new AtomicLong(0);

    private Long getNextVersionId() {
        return versionNumberGenerator.getAndIncrement();
    }

    @PostConstruct
    public void initialize() {
        logger.debug("Initializing ContainerPersistenceService");
    }

    @Override
    public Container save(final Container container) throws PersistenceException {
        final Container containerToSave = (container.getVersion() != null)
            ? container
            : Container.builder(container).withVersion(getNextVersionId()).build();

        return saveToObjectMap(containerToSave);
    }

    private Container saveToObjectMap(final Container containerToSave) {
        final UUID containerId = containerToSave.getUniqueId();
        logger.info("Saving new container with id: {}", containerId);

        objectMap.putIfAbsent(containerId, Lists.newArrayList());

        objectMap.compute(containerId, (key, value) -> {
             value.add(containerToSave);
             return value;
        });

        return containerToSave;
    }

    @Override
    public ImmutableList<Container> getAll() throws PersistenceException {
        return objectMap.keySet().stream()
        .map(this::retrieveLatestVersion)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
    }
    @Override
    public Optional<Container> get(final String paramName) throws PersistenceException {
        return objectMap.keySet().stream()
            .map(this::get)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(container -> paramName.equals(container.getName()))
            .findFirst();
    }

    @Override
    public Optional<Container> get(final UUID id) throws PersistenceException {
        return retrieveLatestVersion(id);
    }

    @Override
    public Optional<Container> get(final UUID id, final Long versionNumber) throws PersistenceException {
        final List<Container> versions = retrieveAllVersions(id);

        if ((versions == null) || (versions.isEmpty())) {
            return Optional.empty();
        }

        return versions.stream()
            .filter(v -> v.getVersion().equals(versionNumber))
            .findFirst();
    }

    private Optional<Container> retrieveLatestVersion(final UUID id) {
        final List<Container> versions = retrieveAllVersions(id);

        if ((versions == null) || (versions.isEmpty())) {
            return Optional.empty();
        }

        return Optional.of(versions.get(versions.size() - 1));
    }

    private List<Container> retrieveAllVersions(final UUID id) {
        return objectMap.get(id);
    }
}
