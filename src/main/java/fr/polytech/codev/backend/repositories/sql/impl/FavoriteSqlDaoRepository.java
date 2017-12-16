package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Favorite;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class FavoriteSqlDaoRepository extends AbstractSqlDaoRepository<Favorite> {

    @Override
    public Class<Favorite> getEntityClass() {
        return Favorite.class;
    }
}