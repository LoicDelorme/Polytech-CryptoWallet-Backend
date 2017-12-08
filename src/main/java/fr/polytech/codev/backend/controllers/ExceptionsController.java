package fr.polytech.codev.backend.controllers;

import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.responses.FailureResponse;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

@RestControllerAdvice
public class ExceptionsController extends AbstractController {

    public static final String DEFAULT_ERROR_MESSAGE = "The requested operation has thrown an exception.";

    public static final String DEFAULT_WRAPPED_ERROR_MESSAGE = "Request '%s' raised an exception:\n%s";

    public static final String DEFAULT_UNKNOWN_ENTITY_ERROR_MESSAGE = "The specified ID refer to an unknown entity.";

    public static final String DEFAULT_INVALID_ENTITY_ERROR_MESSAGE = "The provided information are not compliant with defined constraints.";

    public static final String DEFAULT_INVALID_TOKEN_ERROR_MESSAGE = "The specified token is invalid.";

    public static final String DEFAULT_EXPIRED_TOKEN_ERROR_MESSAGE = "The specified token has expired.";

    public static final String DEFAULT_UNAUTHORIZED_USER_ERROR_MESSAGE = "The specified user is not granted to perform this operation.";

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity handleHibernateException(HttpServletRequest request, HibernateException exception) {
        return generateResponseEntity(request, DEFAULT_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(UnknownEntityException.class)
    public ResponseEntity handleUnknownEntityException(HttpServletRequest request, UnknownEntityException exception) {
        return generateResponseEntity(request, DEFAULT_UNKNOWN_ENTITY_ERROR_MESSAGE, HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity handleInvalidEntityException(HttpServletRequest request, InvalidEntityException exception) {
        return generateResponseEntity(request, DEFAULT_INVALID_ENTITY_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleInvalidTokenException(HttpServletRequest request, InvalidTokenException exception) {
        return generateResponseEntity(request, DEFAULT_INVALID_TOKEN_ERROR_MESSAGE, HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity handleExpiredTokenException(HttpServletRequest request, ExpiredTokenException exception) {
        return generateResponseEntity(request, DEFAULT_EXPIRED_TOKEN_ERROR_MESSAGE, HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity handleUnauthorizedUserException(HttpServletRequest request, UnauthorizedUserException exception) {
        return generateResponseEntity(request, DEFAULT_UNAUTHORIZED_USER_ERROR_MESSAGE, HttpStatus.UNAUTHORIZED, exception);
    }

    private ResponseEntity generateResponseEntity(HttpServletRequest request, String message, HttpStatus httpStatus, Exception exception) {
        final String serializedContent = serializeContent(message, request.getRequestURL().toString(), exception);

        logError(serializedContent);
        return ResponseEntity.status(httpStatus).body(serializedContent);
    }

    private String serializeContent(String message, String requestURL, Exception exception) {
        final Writer writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));

        return serialize(new FailureResponse(message, String.format(DEFAULT_WRAPPED_ERROR_MESSAGE, requestURL, writer.toString())));
    }
}