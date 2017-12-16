package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.AlertType;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class AlertTypeSqlDaoRepository extends AbstractSqlDaoRepository<AlertType> {

    @Override
    public Class<AlertType> getEntityClass() {
        return AlertType.class;
    }
}