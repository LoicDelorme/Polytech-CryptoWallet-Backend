package fr.polytech.codev.backend.controllers;

import fr.polytech.codev.backend.deserializers.AbstractStringDeserializer;
import fr.polytech.codev.backend.deserializers.JsonStringDeserializer;
import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.ExpiredTokenException;
import fr.polytech.codev.backend.exceptions.InvalidTokenException;
import fr.polytech.codev.backend.exceptions.UnauthorizedUserException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.responses.AbstractResponse;
import fr.polytech.codev.backend.responses.FailureResponse;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.serializers.AbstractStringSerializer;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.impl.TokenServices;
import fr.polytech.codev.backend.services.impl.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractController {

    public static final int DEFAULT_THEME_ID_VALUE = 1;

    public static final int DEFAULT_CURRENCY_ID_VALUE = 1;

    public static final int DEFAULT_CHART_PERIOD_ID_VALUE = 1;

    public static final boolean DEFAULT_IS_ADMINISTRATOR_VALUE = false;

    public static final boolean DEFAULT_IS_ENABLED_VALUE = true;

    public static final int DEFAULT_TOKEN_END_DATE_PLUS_DAY_VALUE = 7;

    private static Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private static AbstractStringSerializer abstractStringSerializer = new JsonStringSerializer();

    private static AbstractStringDeserializer abstractStringDeserializer = new JsonStringDeserializer();

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private UserServices userServices;

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

    protected <I> ResponseEntity serializeSuccessResponse(String message, I data) {
        return serializeResponse(HttpStatus.OK, new SuccessResponse(message, data));
    }

    protected ResponseEntity serializeSuccessResponse(String message) {
        return serializeSuccessResponse(message, null);
    }

    protected <I> ResponseEntity serializeSuccessResponse(I data) {
        return serializeSuccessResponse(null, data);
    }

    protected <I> ResponseEntity serializeFailureResponse(HttpStatus status, String message, I data) {
        return serializeResponse(status, new FailureResponse(message, data));
    }

    protected <O> O deserialize(String data, Class<O> outputType) {
        return abstractStringDeserializer.from(data, outputType);
    }

    protected User assertIsUser(String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);

        final User user = token.getUser();
        this.userServices.updateLastActivity(user.getId());

        return user;
    }

    public User assertUserIsAdministrator(String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);
        assertUserIsAdministrator(token);

        final User user = token.getUser();
        this.userServices.updateLastActivity(user.getId());

        return user;
    }

    private Token getToken(String tokenValue) throws UnknownEntityException, InvalidTokenException {
        final List<Token> tokens = this.tokenServices.getByValue(tokenValue);
        if (tokens.isEmpty()) {
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

    private void assertUserIsAdministrator(Token token) throws UnauthorizedUserException {
        if (!token.getUser().isAdministrator()) {
            throw new UnauthorizedUserException();
        }
    }
}