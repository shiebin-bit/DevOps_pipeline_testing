package de.bach.spring.crud.exception;

@SuppressWarnings("PMD")
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(final String message) {
        super(message);
    }
}
