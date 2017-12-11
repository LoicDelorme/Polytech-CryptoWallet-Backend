package fr.polytech.codev.backend.controllers.services;

import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.TokenForm;
import fr.polytech.codev.backend.services.TokenSqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TokenControllerServices extends AbstractControllerServices {

    @Autowired
    private TokenSqlDaoServices tokenSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    public List<Token> all() throws UnknownEntityException {
        final List<Token> tokens = this.tokenSqlDaoServices.getAll();
        if (tokens == null) {
            throw new UnknownEntityException();
        }

        return tokens;
    }

    public Token get(int id) throws UnknownEntityException {
        final Token token = this.tokenSqlDaoServices.get(id);
        if (token == null) {
            throw new UnknownEntityException();
        }

        return token;
    }

    public Token insert(TokenForm tokenForm) throws InvalidEntityException {
        final Token token = new Token();
        token.setValue(UUID.randomUUID().toString());
        token.setBeginDate(LocalDateTime.now());
        token.setEndDate(tokenForm.getEndDate());
        token.setCreationDate(LocalDateTime.now());
        token.setLastUpdate(LocalDateTime.now());
        token.setUser(this.userSqlDaoServices.get(tokenForm.getUserId()));

        validate(token);
        this.tokenSqlDaoServices.insert(token);

        return token;
    }

    public Token update(int id, TokenForm tokenForm) throws UnknownEntityException, InvalidEntityException {
        final Token token = this.tokenSqlDaoServices.get(id);
        if (token == null) {
            throw new UnknownEntityException();
        }

        token.setEndDate(tokenForm.getEndDate());
        token.setLastUpdate(LocalDateTime.now());

        validate(token);
        this.tokenSqlDaoServices.update(token);

        return token;
    }

    public void delete(int id) throws UnknownEntityException {
        final Token token = this.tokenSqlDaoServices.get(id);
        if (token == null) {
            throw new UnknownEntityException();
        }

        this.tokenSqlDaoServices.delete(token);
    }
}