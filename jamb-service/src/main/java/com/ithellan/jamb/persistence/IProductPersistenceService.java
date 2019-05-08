package com.ithellan.jamb.persistence;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.PersistenceException;

import com.ithellan.jamb.domain.Product;


public interface IProductPersistenceService extends IGenericPersistenceService<UUID, Product> {
    String FIELD_NAME_NAME = "name";

    Optional<Product> get(final String name) throws PersistenceException;
}
