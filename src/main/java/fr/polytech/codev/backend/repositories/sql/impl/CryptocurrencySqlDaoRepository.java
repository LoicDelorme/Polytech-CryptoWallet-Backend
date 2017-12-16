package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class CryptocurrencySqlDaoRepository extends AbstractSqlDaoRepository<Cryptocurrency> {

    @Override
    public Class<Cryptocurrency> getEntityClass() {
        return Cryptocurrency.class;
    }
}