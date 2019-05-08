package com.ithellan.jamb.domain;



import java.time.ZonedDateTime;

import com.ithellam.common.utils.IBuilder;

/**
 * Helper base class for implementing the builder pattern for domain models
 *
 * @param <E>
 *            The model class
 * @param <K>
 *            The unique ID type for that model
 */
public abstract class AbstractPersistedObjectBuilder<E extends AbstractPersistedObject<K>, K> implements IBuilder<E> {
    private K uniqueId;
    private Long version;
    private ZonedDateTime created;

    @Override
    public AbstractPersistedObjectBuilder<E, K> fromRecord(final E record) {
        this.onFromRecord(record);
        return withUniqueId(record.getUniqueId()).withVersion(record.getVersion()).withCreated(record.getCreated());
    }

    public K getUniqueId() {
        return uniqueId;
    }

    public Long getVersion() {
        return version;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    /**
     * Lifecycle hook to reconcile model-specific fields into an existing builder
     *
     * @param record
     *            The incoming model
     */
    protected abstract void onFromRecord(final E record);

    public AbstractPersistedObjectBuilder<E, K> withUniqueId(final K uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public AbstractPersistedObjectBuilder<E, K> withVersion(final Long version) {
        this.version = version;
        return this;
    }

    private AbstractPersistedObjectBuilder<E, K> withCreated(final ZonedDateTime created) {
        this.created = created;
        return this;
    }
}
