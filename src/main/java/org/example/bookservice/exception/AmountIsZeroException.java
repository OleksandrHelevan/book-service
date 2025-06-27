package org.example.bookservice.exception;

public class AmountIsZeroException extends RuntimeException {
    public AmountIsZeroException(String message) {
        super(message);
    }

    public AmountIsZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmountIsZeroException(Throwable cause) {
        super(cause);
    }
}
