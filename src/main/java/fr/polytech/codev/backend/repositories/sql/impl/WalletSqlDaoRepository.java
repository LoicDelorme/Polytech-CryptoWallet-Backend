package fr.polytech.codev.backend.repositories.sql.impl;

import fr.polytech.codev.backend.entities.Wallet;
import fr.polytech.codev.backend.repositories.sql.AbstractSqlDaoRepository;

public class WalletSqlDaoRepository extends AbstractSqlDaoRepository<Wallet> {

    @Override
    public Class<Wallet> getEntityClass() {
        return Wallet.class;
    }
}