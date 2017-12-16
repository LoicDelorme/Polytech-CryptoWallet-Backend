package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class AlertSqlDaoServices extends AbstractSqlDaoServices<Alert> {

    @Override
    public Class<Alert> getEntityClass() {
        return Alert.class;
    }
}