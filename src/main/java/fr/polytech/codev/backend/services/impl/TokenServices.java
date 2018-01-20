package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.TokenForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TokenServices extends AbstractServices {

    @Autowired
    private DaoRepository<Token> tokenDaoRepository;

    @Autowired
    private DaoRepository<User> userDaoRepository;

    public List<Token> all() throws UnknownEntityException {
        final List<Token> tokens = this.tokenDaoRepository.getAll();
        if (tokens == null) {
            throw new UnknownEntityException();
        }

        return tokens;
    }

    public Token get(int id) throws UnknownEntityException {
        final Token token = this.tokenDaoRepository.get(id);
        if (token == null) {
            throw new UnknownEntityException();
        }

        return token;
    }

    public List<Token> getByValue(String value) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("value", value);

        final List<Token> tokens = this.tokenDaoRepository.filter(parameters);
        if (tokens == null) {
            throw new UnknownEntityException();
        }

        return tokens;
    }

    public Token insert(TokenForm tokenForm) throws InvalidEntityException {
        final Token token = new Token();
        token.setValue(UUID.randomUUID().toString());
        token.setBeginDate(LocalDateTime.now());
        token.setEndDate(tokenForm.getEndDate());
        token.setPlatform(tokenForm.getPlatform());
        token.setCreationDate(LocalDateTime.now());
        token.setLastUpdate(LocalDateTime.now());
        token.setUser(this.userDaoRepository.get(tokenForm.getUserId()));

        validate(token);
        this.tokenDaoRepository.insert(token);

        return token;
    }

    public Token update(int id, TokenForm tokenForm) throws UnknownEntityException, InvalidEntityException {
        final Token token = get(id);
        token.setEndDate(tokenForm.getEndDate());
        token.setPlatform(tokenForm.getPlatform());
        token.setLastUpdate(LocalDateTime.now());

        validate(token);
        this.tokenDaoRepository.update(token);

        return token;
    }

    public void delete(int id) throws UnknownEntityException {
        this.tokenDaoRepository.delete(get(id));
    }
}