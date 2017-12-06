package fr.polytech.codev.backend.services;

import fr.polytech.codev.backend.entities.Token;

public class TokenSqlDaoServices extends AbstractSqlDaoServices<Token> {

    @Override
    public Class<Token> getEntityClass() {
        return Token.class;
    }
}