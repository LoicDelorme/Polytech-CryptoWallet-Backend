package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Asset;
import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.entities.Wallet;
import fr.polytech.codev.backend.entities.pks.AssetPk;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AssetForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetServices extends AbstractServices {

    @Autowired
    private DaoRepository<Asset> assetDaoRepository;

    @Autowired
    private DaoRepository<Cryptocurrency> cryptocurrencyDaoRepository;

    @Autowired
    private DaoRepository<Wallet> walletDaoRepository;

    public List<Asset> all() throws UnknownEntityException {
        final List<Asset> assets = this.assetDaoRepository.getAll();
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public Asset get(int walletId, int cryptocurrencyId) throws UnknownEntityException {
        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletDaoRepository.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencyDaoRepository.get(cryptocurrencyId));

        final Asset asset = this.assetDaoRepository.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        return asset;
    }

    public List<Asset> getByWallet(int walletId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("wallet", this.walletDaoRepository.get(walletId));

        final List<Asset> assets = this.assetDaoRepository.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public List<Asset> getByCryptocurrency(int cryptocurrencyId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencyDaoRepository.get(cryptocurrencyId));

        final List<Asset> assets = this.assetDaoRepository.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return assets;
    }

    public Asset insert(int walletId, int cryptocurrencyId, AssetForm assetForm) throws InvalidEntityException {
        final Asset asset = new Asset();
        asset.setWallet(this.walletDaoRepository.get(walletId));
        asset.setCryptocurrency(this.cryptocurrencyDaoRepository.get(cryptocurrencyId));
        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(assetForm.getPurchasePrice());

        validate(asset);
        this.assetDaoRepository.insert(asset);

        return asset;
    }

    public Asset update(int walletId, int cryptocurrencyId, AssetForm assetForm) throws UnknownEntityException, InvalidEntityException {
        final Asset asset = get(walletId, cryptocurrencyId);
        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(assetForm.getPurchasePrice());

        validate(asset);
        this.assetDaoRepository.update(asset);

        return asset;
    }

    public void delete(int walletId, int cryptocurrencyId) throws UnknownEntityException {
        this.assetDaoRepository.delete(get(walletId, cryptocurrencyId));
    }
}