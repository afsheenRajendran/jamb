package com.ithellam.jamb.rest;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to specify which service interface/resource a client side proxy is implementing.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ServiceInterface {
    Class<?> value();
}
