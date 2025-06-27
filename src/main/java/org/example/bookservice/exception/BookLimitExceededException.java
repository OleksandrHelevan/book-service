package org.example.bookservice.exception;

public class BookLimitExceededException extends RuntimeException {
    public BookLimitExceededException(String message) {
        super(message);
    }

}
