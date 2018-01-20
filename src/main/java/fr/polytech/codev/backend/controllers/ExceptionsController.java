package fr.polytech.codev.backend.controllers;

import fr.polytech.codev.backend.exceptions.*;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.json.bind.JsonbException;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

@RestControllerAdvice
public class ExceptionsController extends AbstractController {

    public static final String DEFAULT_ERROR_MESSAGE = "The requested operation has thrown an exception.";

    public static final String DEFAULT_WRAPPED_ERROR_MESSAGE = "Request '%s' raised an exception:\n%s";

    public static final String DEFAULT_UNKNOWN_ENTITY_ERROR_MESSAGE = "The specified ID refer to an unknown entity.";

    public static final String DEFAULT_UNKNOWN_TOKEN_ERROR_MESSAGE = "The specified token refer to an unknown entity.";

    public static final String DEFAULT_INVALID_ENTITY_ERROR_MESSAGE = "The provided information are not compliant with defined constraints.";

    public static final String DEFAULT_INVALID_TOKEN_ERROR_MESSAGE = "The specified token is invalid.";

    public static final String DEFAULT_EXPIRED_TOKEN_ERROR_MESSAGE = "The specified token has expired.";

    public static final String DEFAULT_UNAUTHORIZED_USER_ERROR_MESSAGE = "The specified user is not granted to perform this operation.";

    @ExceptionHandler(JsonbException.class)
    public ResponseEntity handleJsonbException(HttpServletRequest request, JsonbException exception) {
        return generateResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE, exception);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity handleHibernateException(HttpServletRequest request, HibernateException exception) {
        return generateResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE, exception);
    }

    @ExceptionHandler(UnknownEntityException.class)
    public ResponseEntity handleUnknownEntityException(HttpServletRequest request, UnknownEntityException exception) {
        return generateResponseEntity(request, HttpStatus.NOT_FOUND, DEFAULT_UNKNOWN_ENTITY_ERROR_MESSAGE, exception);
    }

    @ExceptionHandler(UnknownTokenException.class)
    public ResponseEntity handleUnknownTokenException(HttpServletRequest request, UnknownTokenException exception) {
        return generateResponseEntity(request, HttpStatus.NOT_FOUND, DEFAULT_UNKNOWN_TOKEN_ERROR_MESSAGE, exception);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity handleInvalidEntityException(HttpServletRequest request, InvalidEntityException exception) {
        return generateResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_INVALID_ENTITY_ERROR_MESSAGE, exception);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleInvalidTokenException(HttpServletRequest request, InvalidTokenException exception) {
        return generateResponseEntity(request, HttpStatus.NOT_FOUND, DEFAULT_INVALID_TOKEN_ERROR_MESSAGE, exception);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity handleExpiredTokenException(HttpServletRequest request, ExpiredTokenException exception) {
        return generateResponseEntity(request, HttpStatus.UNAUTHORIZED, DEFAULT_EXPIRED_TOKEN_ERROR_MESSAGE, exception);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity handleUnauthorizedUserException(HttpServletRequest request, UnauthorizedUserException exception) {
        return generateResponseEntity(request, HttpStatus.UNAUTHORIZED, DEFAULT_UNAUTHORIZED_USER_ERROR_MESSAGE, exception);
    }

    private ResponseEntity generateResponseEntity(HttpServletRequest request, HttpStatus httpStatus, String message, Exception exception) {
        final Writer writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));

        final String exceptionStackTrace = String.format(DEFAULT_WRAPPED_ERROR_MESSAGE, request.getRequestURL().toString(), writer.toString());
        logError(exceptionStackTrace);

        return serializeFailureResponse(httpStatus, message, exceptionStackTrace);
    }
}