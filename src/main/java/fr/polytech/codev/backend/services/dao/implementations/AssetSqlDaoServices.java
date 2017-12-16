package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.Asset;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class AssetSqlDaoServices extends AbstractSqlDaoServices<Asset> {

    @Override
    public Class<Asset> getEntityClass() {
        return Asset.class;
    }
}