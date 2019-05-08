package com.ithellam.jamb.tests;


import static com.ithellam.jamb.rest.PropertyNameUtils.*;
import java.util.Map;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.ithellam.common.utils.IConfigProvider;


public class SimpleConfigProvider implements IConfigProvider {

    private final Map<String, String> propMap = Maps.newHashMap();

    public SimpleConfigProvider() {
        propMap.put(MAIN_SERVICE_URLS_PROPERTY_NAME, MAIN_SERVICE_URLS_PROPERTY_VALUE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProperty(final String propertyName, final Class<T> clazz) {
        if (Enum.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(
            "This method cannot be used to create an enum. Use the one which defines that T extends Enum and requires a default value.");
        }

        final String value = getProperty(propertyName);
        if (value == null) {
            return null;
        }

        if (clazz == String.class) {
            return (T)value;
        } else if (clazz == Boolean.class || clazz == Integer.class || clazz == Long.class || clazz == Short.class ||
        clazz == Float.class || clazz == Double.class) {
            return tryParse(value, clazz);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProperty(final String propertyName, final T defaultValue) {
        Preconditions.checkNotNull(defaultValue, "The 'defaultValue' must not be null.");

        if (Enum.class.isAssignableFrom(defaultValue.getClass())) {
            throw new IllegalArgumentException(
            "This method cannot be used to create an enum. Use the one which defines that T extends Enum and requires a default value.");
        }

        final T result = (T)getProperty(propertyName, defaultValue.getClass());
        return result == null ? defaultValue : result;
    }

    @SuppressWarnings("unchecked")
    private <T> T tryParse(final String value, final Class<T> clazz) {
        try {
            if (clazz == Boolean.class) {
                return (T)new Boolean(Boolean.parseBoolean(value));
            } else if (clazz == Short.class) {
                return (T)new Short(Short.parseShort(value));
            } else if (clazz == Integer.class) {
                return (T)new Integer(Integer.parseInt(value));
            } else if (clazz == Long.class) {
                return (T)new Long(Long.parseLong(value));
            } else if (clazz == Float.class) {
                return (T)new Float(Float.parseFloat(value));
            } else if (clazz == Double.class) {
                return (T)new Double(Double.parseDouble(value));
            }
        } catch (final Exception ex) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Enum<T>> T getProperty(final String propertyName, final T defaultValue) {
        final String value = getProperty(propertyName);
        if (Strings.isNullOrEmpty(value)) {
            return defaultValue;
        }
        return (T)Enum.valueOf(defaultValue.getClass(), value);
    }

    private String getProperty(final String propertyName) {
        return propMap.get(propertyName);
    }
}
