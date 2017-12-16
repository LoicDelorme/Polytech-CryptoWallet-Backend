package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class LogSqlDaoRepository extends AbstractSqlDaoRepository<Log> {

    @Override
    public Class<Log> getEntityClass() {
        return Log.class;
    }
}