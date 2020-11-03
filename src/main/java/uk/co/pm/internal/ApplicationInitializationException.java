package uk.co.pm.internal;

import java.io.IOException;

public class ApplicationInitializationException extends RuntimeException {
    public ApplicationInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationInitializationException(String message) {
        super(message);
    }
}
