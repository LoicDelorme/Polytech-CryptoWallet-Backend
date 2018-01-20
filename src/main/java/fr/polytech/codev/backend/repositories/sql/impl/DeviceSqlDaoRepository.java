package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Device;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class DeviceSqlDaoRepository extends AbstractSqlDaoRepository<Device> {

    @Override
    public Class<Device> getEntityClass() {
        return Device.class;
    }
}