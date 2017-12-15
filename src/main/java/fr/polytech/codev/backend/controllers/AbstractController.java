package fr.polytech.codev.backend.controllers;

import fr.polytech.codev.backend.controllers.services.TokenControllerServices;
import fr.polytech.codev.backend.controllers.services.UserControllerServices;
import fr.polytech.codev.backend.deserializers.AbstractStringDeserializer;
import fr.polytech.codev.backend.deserializers.JsonStringDeserializer;
import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.exceptions.ExpiredTokenException;
import fr.polytech.codev.backend.exceptions.InvalidTokenException;
import fr.polytech.codev.backend.exceptions.UnauthorizedUserException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.responses.AbstractResponse;
import fr.polytech.codev.backend.responses.FailureResponse;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.serializers.AbstractStringSerializer;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractController {

    private static Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private static AbstractStringSerializer abstractStringSerializer = new JsonStringSerializer();

    private static AbstractStringDeserializer abstractStringDeserializer = new JsonStringDeserializer();

    @Autowired
    private TokenControllerServices tokenControllerServices;

    @Autowired
    private UserControllerServices userControllerServices;

    protected void logInfo(String message) {
        logger.info(message);
    }

    protected void logDebug(String message) {
        logger.debug(message);
    }

    protected void logWarn(String message) {
        logger.warn(message);
    }

    protected void logError(String message) {
        logger.error(message);
    }

    private ResponseEntity serializeResponse(HttpStatus status, AbstractResponse response) {
        return ResponseEntity.status(status).body(abstractStringSerializer.to(response));
    }

    protected ResponseEntity serializeSuccessResponse() {
        return serializeResponse(HttpStatus.OK, new SuccessResponse());
    }

    protected <I> ResponseEntity serializeSuccessResponse(I data) {
        return serializeResponse(HttpStatus.OK, new SuccessResponse(data));
    }

    protected <I> ResponseEntity serializeFailureResponse(HttpStatus status, String message, I data) {
        return serializeResponse(status, new FailureResponse(message, data));
    }

    public <O> O deserialize(String data, Class<O> outputType) {
        return abstractStringDeserializer.from(data, outputType);
    }

    public void assertIsUser(String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);
    }

    public void assertUserIsUser(String tokenValue, int requestedId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);
        assertUserIsUser(token, requestedId);
        this.userControllerServices.updateLastActivity(token.getUser().getId());
    }

    public void assertUserIsAdministrator(String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);
        assertUserIsAdministrator(token);
        this.userControllerServices.updateLastActivity(token.getUser().getId());
    }

    private Token getToken(String tokenValue) throws UnknownEntityException, InvalidTokenException {
        final List<Token> tokens = this.tokenControllerServices.getByValue(tokenValue);
        if (tokens.size() != 1) {
            throw new InvalidTokenException();
        }

        return tokens.get(0);
    }

    private void assertTokenIsValid(Token token) throws ExpiredTokenException {
        if (token.getEndDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException();
        }
    }

    private void assertUserIsEnabled(Token token) throws UnauthorizedUserException {
        if (!token.getUser().isEnabled()) {
            throw new UnauthorizedUserException();
        }
    }

    private void assertUserIsUser(Token token, int requestedId) throws UnauthorizedUserException {
        if (token.getUser().getId() != requestedId) {
            throw new UnauthorizedUserException();
        }
    }

    private void assertUserIsAdministrator(Token token) throws UnauthorizedUserException {
        if (!token.getUser().isAdministrator()) {
            throw new UnauthorizedUserException();
        }
    }
}