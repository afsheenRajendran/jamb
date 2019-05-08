package com.ithellam.common.utils;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class ObjectValidator<U extends Throwable> implements IObjectValidator<U> {
    @Inject
    private Validator validator;
    private Function<String, U> exceptionSupplier;

    @Override
    public ObjectValidator<U> onFailure(final Function<String, U> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
        return this;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validate(final T validatingObject) throws U {
        final Set<ConstraintViolation<T>> violations = Optional.ofNullable(validatingObject)
                .map(obj -> validator.validate(obj))
                .orElseThrow(() -> exceptionSupplier.apply("Object to validate was null."));

        if (!violations.isEmpty() && exceptionSupplier != null) {
            final String messages = violations.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));

            throw exceptionSupplier.apply(messages);
        }

        return violations;
    }
}
