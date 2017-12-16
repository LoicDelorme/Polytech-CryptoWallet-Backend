package fr.polytech.codev.backend.services.dao;

import fr.polytech.codev.backend.entities.Wallet;

public class WalletSqlDaoServices extends AbstractSqlDaoServices<Wallet> {

    @Override
    public Class<Wallet> getEntityClass() {
        return Wallet.class;
    }
}