package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class LogSqlDaoServices extends AbstractSqlDaoServices<Log> {

    @Override
    public Class<Log> getEntityClass() {
        return Log.class;
    }
}