package fr.polytech.codev.backend.services;

import fr.polytech.codev.backend.entities.Cryptocurrency;

public class CryptocurrencySqlDaoServices extends AbstractSqlDaoServices<Cryptocurrency> {

    @Override
    public Class<Cryptocurrency> getEntityClass() {
        return Cryptocurrency.class;
    }
}