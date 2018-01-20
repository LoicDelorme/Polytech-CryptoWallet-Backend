package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Theme;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class ThemeSqlDaoRepository extends AbstractSqlDaoRepository<Theme> {

    @Override
    public Class<Theme> getEntityClass() {
        return Theme.class;
    }
}