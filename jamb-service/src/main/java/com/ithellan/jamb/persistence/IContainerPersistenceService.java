package com.ithellan.jamb.persistence;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.PersistenceException;
import com.ithellan.jamb.domain.Container;

public interface IContainerPersistenceService extends IGenericPersistenceService<UUID, Container> {
    String FIELD_NAME_NAME = "name";

    Optional<Container> get(final String name) throws PersistenceException;
}
