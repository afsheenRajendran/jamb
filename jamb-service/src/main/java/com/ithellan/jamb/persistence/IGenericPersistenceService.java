package com.ithellan.jamb.persistence;

import java.util.Optional;

import javax.persistence.PersistenceException;

import com.google.common.collect.ImmutableList;
import com.ithellan.jamb.domain.AbstractPersistedObject;

public interface IGenericPersistenceService<K, T extends AbstractPersistedObject<K>> {
    ImmutableList<T> getAll() throws PersistenceException;

    /**
     * Get the latest version of the specified type/object if it exists.
     *
     * @param id
     *            The id of the type/object to retrieve.
     * @return The type/object
     * @throws PersistenceException
     */
    Optional<T> get(K id) throws PersistenceException;

    /**
     * Get the specified version of the specified type/object if it exists.
     *
     * @param id
     *            The id of the type/object to retrieve.
     * @param version
     *            The version of the type/object to retrieve.
     * @return The type/object
     * @throws PersistenceException
     */
    Optional<T> get(K id, Long version) throws PersistenceException;

    /**
     * Persist the specified type/object to the persistent store.
     *
     * @param obj
     *            The type/object to persist
     * @return Passed in object without doing additional get query
     * @throws PersistenceException
     */
    T save(T obj) throws PersistenceException;
}
