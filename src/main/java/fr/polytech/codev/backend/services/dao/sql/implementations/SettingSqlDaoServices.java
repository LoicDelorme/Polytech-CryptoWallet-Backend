package fr.polytech.codev.backend.services.dao.sql.implementations;

import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.services.dao.sql.AbstractSqlDaoServices;

public class SettingSqlDaoServices extends AbstractSqlDaoServices<Setting> {

    @Override
    public Class<Setting> getEntityClass() {
        return Setting.class;
    }
}