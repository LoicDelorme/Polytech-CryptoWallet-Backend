package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.Favorite;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class FavoriteSqlDaoServices extends AbstractSqlDaoServices<Favorite> {

    @Override
    public Class<Favorite> getEntityClass() {
        return Favorite.class;
    }
}