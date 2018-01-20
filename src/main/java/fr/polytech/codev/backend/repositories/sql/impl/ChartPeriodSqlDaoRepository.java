package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.ChartPeriod;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class ChartPeriodSqlDaoRepository extends AbstractSqlDaoRepository<ChartPeriod> {

    @Override
    public Class<ChartPeriod> getEntityClass() {
        return ChartPeriod.class;
    }
}