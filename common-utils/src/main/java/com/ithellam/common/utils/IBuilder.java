package com.ithellam.common.utils;

/**
 * Simple interface for implementing the builder pattern
 *
 * @param <T>
 *            The type being built
 */
public interface IBuilder<T> {
    T build();

    IBuilder<T> fromRecord(T record);
}
