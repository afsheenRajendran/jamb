package com.ithellam.jamb.api;

public class MyValidationException extends RuntimeException {
    private static final long serialVersionUID = -2106282533179692274L;

    public MyValidationException() {
        super();
    }

    public MyValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyValidationException(final String message) {
        super(message);
    }

    public MyValidationException(final Throwable cause) {
        super(cause);
    }
}
