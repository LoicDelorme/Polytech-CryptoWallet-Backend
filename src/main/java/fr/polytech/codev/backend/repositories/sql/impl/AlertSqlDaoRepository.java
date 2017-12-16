package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class AlertSqlDaoRepository extends AbstractSqlDaoRepository<Alert> {

    @Override
    public Class<Alert> getEntityClass() {
        return Alert.class;
    }
}