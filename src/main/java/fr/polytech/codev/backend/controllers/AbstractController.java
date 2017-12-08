package fr.polytech.codev.backend.controllers;

import fr.polytech.codev.backend.deserializers.AbstractStringDeserializer;
import fr.polytech.codev.backend.deserializers.JsonStringDeserializer;
import fr.polytech.codev.backend.entities.Entity;
import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.exceptions.ExpiredTokenException;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.InvalidTokenException;
import fr.polytech.codev.backend.exceptions.UnauthorizedUserException;
import fr.polytech.codev.backend.serializers.AbstractStringSerializer;
import fr.polytech.codev.backend.serializers.JsonStringSerializer;
import fr.polytech.codev.backend.services.TokenSqlDaoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractController {

    private static Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private static AbstractStringSerializer abstractStringSerializer = new JsonStringSerializer();

    private static AbstractStringDeserializer abstractStringDeserializer = new JsonStringDeserializer();

    @Autowired
    private TokenSqlDaoServices tokenSqlDaoServices;

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logDebug(String message) {
        logger.debug(message);
    }

    public void logWarn(String message) {
        logger.warn(message);
    }

    public void logError(String message) {
        logger.error(message);
    }

    public <I> String serialize(I data) {
        return abstractStringSerializer.to(data);
    }

    public <O> O deserialize(String data, Class<O> outputType) {
        return abstractStringDeserializer.from(data, outputType);
    }

    public void validate(Entity entity) throws InvalidEntityException {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        final Set<ConstraintViolation<Entity>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream().map(violation -> violation.getMessage()).collect(Collectors.joining(", ")));
        }
    }

    public void assertIsUser(String tokenValue) throws InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);
    }

    public void assertUserIsUser(String tokenValue, int requestedId) throws InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);
        assertUserIsUser(token, requestedId);
    }

    public void assertUserIsAdministrator(String tokenValue) throws InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final Token token = getToken(tokenValue);
        assertTokenIsValid(token);
        assertUserIsEnabled(token);
        assertUserIsAdministrator(token);
    }

    public void assertUserIsUserOrAdministrator(String tokenValue, int id) throws InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        try {
            assertUserIsUser(tokenValue, id);
        } catch (InvalidTokenException | ExpiredTokenException | UnauthorizedUserException exception) {
            assertUserIsAdministrator(tokenValue);
        }
    }

    private Token getToken(String tokenValue) throws InvalidTokenException {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("value", tokenValue);

        final List<Token> tokens = this.tokenSqlDaoServices.filter(parameters);
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