package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Asset;
import fr.polytech.codev.backend.entities.pks.AssetPk;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AssetForm;
import fr.polytech.codev.backend.services.AbstractServices;
import fr.polytech.codev.backend.repositories.sql.impl.AssetSqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.CryptocurrencySqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.WalletSqlDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetServices extends AbstractServices {

    @Autowired
    private AssetSqlDaoRepository assetSqlDaoRepository;

    @Autowired
    private CryptocurrencySqlDaoRepository cryptocurrencySqlDaoRepository;

    @Autowired
    private WalletSqlDaoRepository walletSqlDaoRepository;

    public List<Asset> all() throws UnknownEntityException {
        final List<Asset> assets = this.assetSqlDaoRepository.getAll();
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public Asset get(int walletId, int cryptocurrencyId) throws UnknownEntityException {
        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoRepository.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoRepository.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        return asset;
    }

    public List<Asset> getByWallet(int walletId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("wallet", this.walletSqlDaoRepository.get(walletId));

        final List<Asset> assets = this.assetSqlDaoRepository.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public List<Asset> getByCryptocurrency(int cryptocurrencyId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        final List<Asset> assets = this.assetSqlDaoRepository.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public Asset insert(int walletId, int cryptocurrencyId, AssetForm assetForm) throws InvalidEntityException {
        final Asset asset = new Asset();
        asset.setWallet(this.walletSqlDaoRepository.get(walletId));
        asset.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));
        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(assetForm.getPurchasePrice());

        validate(asset);
        this.assetSqlDaoRepository.insert(asset);

        return asset;
    }

    public Asset update(int walletId, int cryptocurrencyId, AssetForm assetForm) throws UnknownEntityException, InvalidEntityException {
        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoRepository.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoRepository.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(assetForm.getPurchasePrice());

        validate(asset);
        this.assetSqlDaoRepository.update(asset);

        return asset;
    }

    public void delete(int walletId, int cryptocurrencyId) throws UnknownEntityException {
        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoRepository.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoRepository.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        this.assetSqlDaoRepository.delete(asset);
    }
}