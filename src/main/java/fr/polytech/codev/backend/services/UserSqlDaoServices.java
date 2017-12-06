package fr.polytech.codev.backend.services;

import fr.polytech.codev.backend.entities.User;

public class UserSqlDaoServices extends AbstractSqlDaoServices<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}