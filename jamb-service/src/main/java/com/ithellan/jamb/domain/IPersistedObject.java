package com.ithellan.jamb.domain;

import java.time.ZonedDateTime;

public interface IPersistedObject {
    /**
     * Get the created timestamp which will be set once a persisted entity is
     * retrieved from the database.
     */
    ZonedDateTime getCreated();

    /**
     * Get the version from the persisted entity.
     */
    Long getVersion();

    /**
     * Get the principal name from the context when the version was created
     */
    String getExecutingUser();
}
