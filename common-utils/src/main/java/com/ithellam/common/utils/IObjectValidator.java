package com.ithellam.common.utils;

import java.util.Set;
import java.util.function.Function;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * Thin wrapper around the standard Java {@link Validator}, allowing consumers
 * to register an exception supplier upon validation failure
 *
 * @param <U>
 *            The type of exception to throw on failure
 */
public interface IObjectValidator<U extends Throwable> {
    /**
     * Registers a function to supply & throw an exception in the case of a
     * validation failure
     *
     * @param exceptionSupplier
     *            The function to produce the exception
     * @return The validator for fluent API
     */
    ObjectValidator<U> onFailure(Function<String, U> exceptionSupplier);

    /**
     * Validates the passed-in object, throwing an exception if an {@code onFailure}
     * handler has been set
     *
     * @return The set of validations if there is no {@code exceptionSupplier} to
     *         throw an exception
     * @throws U
     *             If any validations fail
     */
    <T> Set<ConstraintViolation<T>> validate(T validatingObject) throws U;
}
