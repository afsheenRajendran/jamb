package com.ithellan.jamb.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.ejb.Startup;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ithellan.jamb.domain.Product;

@Startup
@Singleton
public class ProductPersistenceService implements  IProductPersistenceService {
    private final Map<UUID, List<Product>> objectMap = new ConcurrentHashMap<>();

    final AtomicLong versionNumberGenerator = new AtomicLong(0);

    private Long getNextVersionId() {
        return versionNumberGenerator.getAndIncrement();
    }

    @Override
    public Product save(final Product product) throws PersistenceException {
        final Product productToSave =  (product.getVersion() == null)
          ? Product.builder(product).withVersion(getNextVersionId()).build()
          : product;

        return saveToObjectMap(productToSave);
    }

    private Product saveToObjectMap(final Product productToSave) {
        final UUID productId = productToSave.getUniqueId();

        objectMap.putIfAbsent(productId, Lists.newArrayList());

        objectMap.compute(productId, (key, value) -> {
            value.add(productToSave);
            return value;
        });

        return productToSave;
    }

    @Override
    public ImmutableList<Product> getAll() throws PersistenceException {
        return objectMap.keySet().stream()
            .map(this::retrieveLatestVersion)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Optional<Product> get(final String name) throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public Optional<Product> get(final UUID id) throws PersistenceException {
        return retrieveLatestVersion(id);
    }

    @Override
    public Optional<Product> get(final UUID id, final Long versionNumber) throws PersistenceException {
        final List<Product> versions = retrieveAllVersions(id);

        if ((versions == null) || (versions.isEmpty())) {
            return Optional.empty();
        }

        return versions.stream()
            .filter(v -> v.getVersion().equals(versionNumber))
        .findFirst();
    }


    private Optional<Product> retrieveLatestVersion(final UUID id) {
        final List<Product> versions = retrieveAllVersions(id);

        if ((versions == null) || (versions.isEmpty())) {
            return Optional.empty();
        }

        return Optional.of(versions.get(versions.size() - 1));
    }

    private List<Product> retrieveAllVersions(final UUID id) {
        return objectMap.get(id);
    }
}
