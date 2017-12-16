package fr.polytech.codev.backend.exceptions;

public class UnknownEntityException extends Exception {

    public UnknownEntityException() {
    }

    public UnknownEntityException(String message) {
        super(message);
    }

    public UnknownEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownEntityException(Throwable cause) {
        super(cause);
    }

    public UnknownEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}