package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.AlertType;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class AlertTypeSqlDaoServices extends AbstractSqlDaoServices<AlertType> {

    @Override
    public Class<AlertType> getEntityClass() {
        return AlertType.class;
    }
}