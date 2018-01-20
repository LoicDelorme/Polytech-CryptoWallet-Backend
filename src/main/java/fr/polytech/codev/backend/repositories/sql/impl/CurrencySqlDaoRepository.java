package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Currency;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class CurrencySqlDaoRepository extends AbstractSqlDaoRepository<Currency> {

    @Override
    public Class<Currency> getEntityClass() {
        return Currency.class;
    }
}