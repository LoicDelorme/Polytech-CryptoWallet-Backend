package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class CryptocurrencySqlDaoServices extends AbstractSqlDaoServices<Cryptocurrency> {

    @Override
    public Class<Cryptocurrency> getEntityClass() {
        return Cryptocurrency.class;
    }
}