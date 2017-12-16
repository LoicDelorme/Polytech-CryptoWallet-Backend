package fr.polytech.codev.backend.services.dao.sql.implementations;

import fr.polytech.codev.backend.entities.Asset;
import fr.polytech.codev.backend.services.dao.sql.AbstractSqlDaoServices;

public class AssetSqlDaoServices extends AbstractSqlDaoServices<Asset> {

    @Override
    public Class<Asset> getEntityClass() {
        return Asset.class;
    }
}