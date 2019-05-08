package com.ithellam.jamb.rest;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Exempts a Resource Client interface method from matching its return type to the corresponding Resource specified by {@link ServiceInterface}.
 * This is especially useful for methods which aren't used as direct endpoints, like sub-resource locators.
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface IgnoreResponseType {

}
