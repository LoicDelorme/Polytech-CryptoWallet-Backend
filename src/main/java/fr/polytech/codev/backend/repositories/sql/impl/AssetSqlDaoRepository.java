package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Asset;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class AssetSqlDaoRepository extends AbstractSqlDaoRepository<Asset> {

    @Override
    public Class<Asset> getEntityClass() {
        return Asset.class;
    }
}