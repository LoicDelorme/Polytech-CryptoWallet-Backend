package fr.polytech.codev.backend.services.dao.implementations;

import fr.polytech.codev.backend.entities.Wallet;
import fr.polytech.codev.backend.services.dao.AbstractSqlDaoServices;

public class WalletSqlDaoServices extends AbstractSqlDaoServices<Wallet> {

    @Override
    public Class<Wallet> getEntityClass() {
        return Wallet.class;
    }
}