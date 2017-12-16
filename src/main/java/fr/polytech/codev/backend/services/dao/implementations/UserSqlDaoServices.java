package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class UserSqlDaoServices extends AbstractSqlDaoServices<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}