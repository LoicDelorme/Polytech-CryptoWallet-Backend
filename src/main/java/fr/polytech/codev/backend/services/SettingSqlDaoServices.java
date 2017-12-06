package fr.polytech.codev.backend.services;

import fr.polytech.codev.backend.entities.Setting;

public class SettingSqlDaoServices extends AbstractSqlDaoServices<Setting> {

    @Override
    public Class<Setting> getEntityClass() {
        return Setting.class;
    }
}