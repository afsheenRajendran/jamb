package com.ithellam.common.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a no-args constructor as the designated constructor for JSON
 * deserialization. The constructor is required, though this annotation has no
 * runtime effects. For most use cases, the constructor can have {@code private}
 * visibility, but for {@link javax.ws.rs.BeanParam} usage, the constructor MUST
 * have {@code public} visibility.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.CONSTRUCTOR })
public @interface DeserializationConstructor {
}
