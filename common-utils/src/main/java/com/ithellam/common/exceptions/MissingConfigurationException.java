package com.ithellam.common.exceptions;

public class MissingConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 4476997452569973278L;

    public MissingConfigurationException(String message) { super(message); }
}
