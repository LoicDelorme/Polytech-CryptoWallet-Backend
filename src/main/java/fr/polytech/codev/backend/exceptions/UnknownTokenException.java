package fr.polytech.codev.backend.exceptions;

public class UnknownTokenException extends Exception {

    public UnknownTokenException() {
    }

    public UnknownTokenException(String message) {
        super(message);
    }

    public UnknownTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTokenException(Throwable cause) {
        super(cause);
    }

    public UnknownTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}