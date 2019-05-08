package com.ithellan.jamb.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

// import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
// import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
// import com.ithellam.common.utils.CustomObjectMapperSerializers;
import com.ithellam.common.utils.ObjectComparator;
// import com.ithellam.mps.annotations.MpsVersion;

public abstract class AbstractPersistedObject<K> implements IPersistedObject {
    //@JsonSerialize(using = CustomObjectMapperSerializers.ZonedDateTimeSerializer.class)
    //@JsonDeserialize(using = CustomObjectMapperSerializers.ZonedDateTimeDeserializer.class)
    private final ZonedDateTime created;
    //@MpsVersion
    private final Long version;
    private final String executingUser = null;

    protected AbstractPersistedObject(final Long version) {
        this.version = version;
        created = null;
    }

    protected AbstractPersistedObject(final Long version, final ZonedDateTime created) {
        this.version = version;
        this.created = created;
    }

    @Override
    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public String getExecutingUser() {
        return executingUser;
    }

    /**
     * Get the unique id for this object
     */
    public abstract K getUniqueId();

    @Override
    public int hashCode() {
        return Objects.hash(version, getUniqueId());
    }

    @Override
    public boolean equals(final Object obj) {
        return ObjectComparator.withLeftHandSide(this).withFieldComparison(AbstractPersistedObject::getVersion)
                .withFieldComparison(AbstractPersistedObject::getUniqueId).compareTo(obj);
    }

    protected ToStringHelper getToStringHelper() {
        return MoreObjects.toStringHelper(this).add("created", created).add("version", version).add("uniqueId",
                getUniqueId());
    }

    @Override
    public String toString() {
        return getToStringHelper().toString();
    }
}

