package fr.polytech.codev.backend.services.controllers;

import fr.polytech.codev.backend.entities.Asset;
import fr.polytech.codev.backend.entities.pks.AssetPk;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AssetForm;
import fr.polytech.codev.backend.services.dao.AssetSqlDaoServices;
import fr.polytech.codev.backend.services.dao.CryptocurrencySqlDaoServices;
import fr.polytech.codev.backend.services.dao.WalletSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetControllerServices extends AbstractControllerServices {

    @Autowired
    private AssetSqlDaoServices assetSqlDaoServices;

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @Autowired
    private WalletSqlDaoServices walletSqlDaoServices;

    public List<Asset> all() throws UnknownEntityException {
        final List<Asset> assets = this.assetSqlDaoServices.getAll();
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public Asset get(int walletId, int cryptocurrencyId) throws UnknownEntityException {
        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoServices.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoServices.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        return asset;
    }

    public List<Asset> getByWallet(int walletId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("wallet", this.walletSqlDaoServices.get(walletId));

        final List<Asset> assets = this.assetSqlDaoServices.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public List<Asset> getByCryptocurrency(int cryptocurrencyId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final List<Asset> assets = this.assetSqlDaoServices.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public Asset insert(int walletId, int cryptocurrencyId, AssetForm assetForm) throws InvalidEntityException {
        final Asset asset = new Asset();
        asset.setWallet(this.walletSqlDaoServices.get(walletId));
        asset.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));
        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(assetForm.getPurchasePrice());

        validate(asset);
        this.assetSqlDaoServices.insert(asset);

        return asset;
    }

    public Asset update(int walletId, int cryptocurrencyId, AssetForm assetForm) throws UnknownEntityException, InvalidEntityException {
        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoServices.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoServices.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(assetForm.getPurchasePrice());

        validate(asset);
        this.assetSqlDaoServices.update(asset);

        return asset;
    }

    public void delete(int walletId, int cryptocurrencyId) throws UnknownEntityException {
        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoServices.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoServices.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        this.assetSqlDaoServices.delete(asset);
    }
}