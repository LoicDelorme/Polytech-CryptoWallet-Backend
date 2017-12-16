package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class UserSqlDaoRepository extends AbstractSqlDaoRepository<User> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}