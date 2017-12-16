package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class SettingSqlDaoRepository extends AbstractSqlDaoRepository<Setting> {

    @Override
    public Class<Setting> getEntityClass() {
        return Setting.class;
    }
}