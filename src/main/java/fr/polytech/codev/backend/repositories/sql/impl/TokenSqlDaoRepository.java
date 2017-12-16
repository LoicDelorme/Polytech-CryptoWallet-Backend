package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class TokenSqlDaoRepository extends AbstractSqlDaoRepository<Token> {

    @Override
    public Class<Token> getEntityClass() {
        return Token.class;
    }
}