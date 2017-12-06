package fr.polytech.codev.backend.services;

import fr.polytech.codev.backend.entities.Alert;

public class AlertSqlDaoServices extends AbstractSqlDaoServices<Alert> {

    @Override
    public Class<Alert> getEntityClass() {
        return Alert.class;
    }
}