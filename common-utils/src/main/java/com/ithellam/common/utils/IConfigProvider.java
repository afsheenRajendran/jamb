package com.ithellam.common.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.ImmutableSortedSet;

public interface IConfigProvider {
    static final Logger logger = LoggerFactory.getLogger(IConfigProvider.class);

    <T> T getProperty(String propertyName, Class<T> clazz);

    <T> T getProperty(String propertyName, T defaultValue);

    <T extends Enum<T>> T getProperty(String propertyName, T defaultValue);

    default Set<String> getSplitStringValues(final String propertyName) {
        final String propertyValue = getProperty(propertyName, "");

        logger.info("Got value: {} for property name: {}", propertyValue, propertyName);

        return Arrays.stream(propertyValue.split(","))
        .filter(Objects::nonNull)
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(ImmutableSortedSet.toImmutableSortedSet(String::compareTo));
    }
}
