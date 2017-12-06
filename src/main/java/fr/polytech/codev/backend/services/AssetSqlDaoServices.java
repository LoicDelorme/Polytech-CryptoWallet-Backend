package fr.polytech.codev.backend.services;

import fr.polytech.codev.backend.entities.Asset;

public class AssetSqlDaoServices extends AbstractSqlDaoServices<Asset> {

    @Override
    public Class<Asset> getEntityClass() {
        return Asset.class;
    }
}