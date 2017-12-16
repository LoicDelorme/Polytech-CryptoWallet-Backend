package fr.polytech.codev.backend.services.dao.sql.implementations;

import fr.polytech.codev.backend.entities.Favorite;
import fr.polytech.codev.backend.services.dao.sql.AbstractSqlDaoServices;

public class FavoriteSqlDaoServices extends AbstractSqlDaoServices<Favorite> {

    @Override
    public Class<Favorite> getEntityClass() {
        return Favorite.class;
    }
}