package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Wallet;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.WalletForm;
import fr.polytech.codev.backend.services.AbstractServices;
import fr.polytech.codev.backend.repositories.sql.impl.UserSqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.WalletSqlDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class WalletServices extends AbstractServices {

    @Autowired
    private WalletSqlDaoRepository walletSqlDaoRepository;

    @Autowired
    private UserSqlDaoRepository userSqlDaoRepository;

    public List<Wallet> all() throws UnknownEntityException {
        final List<Wallet> wallets = this.walletSqlDaoRepository.getAll();
        if (wallets == null) {
            throw new UnknownEntityException();
        }

        return wallets;
    }

    public Wallet get(int id) throws UnknownEntityException {
        final Wallet wallet = this.walletSqlDaoRepository.get(id);
        if (wallet == null) {
            throw new UnknownEntityException();
        }

        return wallet;
    }

    public Wallet insert(WalletForm walletForm) throws InvalidEntityException {
        final Wallet wallet = new Wallet();
        wallet.setName(walletForm.getName());
        wallet.setCreationDate(LocalDateTime.now());
        wallet.setLastUpdate(LocalDateTime.now());
        wallet.setUser(this.userSqlDaoRepository.get(walletForm.getUserId()));

        validate(wallet);
        this.walletSqlDaoRepository.insert(wallet);

        return wallet;
    }

    public Wallet update(int id, WalletForm walletForm) throws UnknownEntityException, InvalidEntityException {
        final Wallet wallet = this.walletSqlDaoRepository.get(id);
        if (wallet == null) {
            throw new UnknownEntityException();
        }

        wallet.setName(walletForm.getName());
        wallet.setLastUpdate(LocalDateTime.now());

        validate(wallet);
        this.walletSqlDaoRepository.update(wallet);

        return wallet;
    }

    public void delete(int id) throws UnknownEntityException {
        final Wallet wallet = this.walletSqlDaoRepository.get(id);
        if (wallet == null) {
            throw new UnknownEntityException();
        }

        this.walletSqlDaoRepository.delete(wallet);
    }
}