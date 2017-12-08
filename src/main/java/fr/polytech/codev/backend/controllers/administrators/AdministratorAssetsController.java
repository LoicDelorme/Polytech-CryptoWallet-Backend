package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Asset;
import fr.polytech.codev.backend.entities.pks.AssetPk;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.AssetForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.AssetSqlDaoServices;
import fr.polytech.codev.backend.services.CryptocurrencySqlDaoServices;
import fr.polytech.codev.backend.services.WalletSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/asset")
public class AdministratorAssetsController extends AbstractController {

    @Autowired
    private AssetSqlDaoServices assetSqlDaoServices;

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @Autowired
    private WalletSqlDaoServices walletSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Asset> assets = this.assetSqlDaoServices.getAll();
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(assets)));
    }

    @RequestMapping(value = "/{walletId}/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoServices.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoServices.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(asset)));
    }

    @RequestMapping(value = "wallet/{walletId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByWallet(@PathVariable String tokenValue, @PathVariable int walletId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("wallet", this.walletSqlDaoServices.get(walletId));

        final List<Asset> assets = this.assetSqlDaoServices.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(assets)));
    }

    @RequestMapping(value = "cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByCryptocurrency(@PathVariable String tokenValue, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final List<Asset> assets = this.assetSqlDaoServices.filter(parameters);
        if (assets == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(assets)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AssetForm assetForm = deserialize(data, AssetForm.class);

        final Asset asset = new Asset();
        asset.setWallet(this.walletSqlDaoServices.get(assetForm.getWalletId()));
        asset.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(assetForm.getCryptocurrencyId()));
        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(assetForm.getPurchasePrice());

        validate(asset);

        this.assetSqlDaoServices.insert(asset);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(asset)));
    }

    @RequestMapping(value = "/{walletId}/{cryptocurrencyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoServices.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoServices.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        final AssetForm assetForm = deserialize(data, AssetForm.class);
        asset.setAmount(assetForm.getAmount());
        asset.setPurchasePrice(asset.getPurchasePrice());

        validate(asset);

        this.assetSqlDaoServices.update(asset);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(asset)));
    }

    @RequestMapping(value = "/{walletId}/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AssetPk assetPk = new AssetPk();
        assetPk.setWallet(this.walletSqlDaoServices.get(walletId));
        assetPk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Asset asset = this.assetSqlDaoServices.get(assetPk);
        if (asset == null) {
            throw new UnknownEntityException();
        }

        this.assetSqlDaoServices.delete(asset);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}