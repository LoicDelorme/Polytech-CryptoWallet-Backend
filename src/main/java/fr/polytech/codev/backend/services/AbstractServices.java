package fr.polytech.codev.backend.services;

import fr.polytech.codev.backend.entities.Entity;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractServices {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public void validate(Entity entity) throws InvalidEntityException {
        final Set<ConstraintViolation<Entity>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream().map(violation -> violation.getMessage()).collect(Collectors.joining(", ")));
        }
    }
}