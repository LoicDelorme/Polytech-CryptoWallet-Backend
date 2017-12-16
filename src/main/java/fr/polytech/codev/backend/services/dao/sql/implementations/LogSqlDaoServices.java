package fr.polytech.codev.backend.services.dao.sql.implementations;

import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.services.dao.sql.AbstractSqlDaoServices;

public class LogSqlDaoServices extends AbstractSqlDaoServices<Log> {

    @Override
    public Class<Log> getEntityClass() {
        return Log.class;
    }
}