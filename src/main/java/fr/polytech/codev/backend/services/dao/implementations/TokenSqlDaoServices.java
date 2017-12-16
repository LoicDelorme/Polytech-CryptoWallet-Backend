package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class TokenSqlDaoServices extends AbstractSqlDaoServices<Token> {

    @Override
    public Class<Token> getEntityClass() {
        return Token.class;
    }
}