package fr.polytech.codev.backend.controllers.registered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.*;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.*;
import fr.polytech.codev.backend.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/registered/{token}/user")
public class RegisteredUserController extends AbstractController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private FavoriteServices favoriteServices;

    @Autowired
    private CryptocurrencyServices cryptocurrencyServices;

    @Autowired
    private WalletServices walletServices;

    @Autowired
    private AssetServices assetServices;

    @Autowired
    private AlertServices alertServices;

    @Autowired
    private SettingServices settingServices;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private LogServices logServices;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.userServices.get(userId));
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String token, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setAdministrator(user.isAdministrator());
        userForm.setEnabled(user.isEnabled());

        return serializeSuccessResponse(this.userServices.update(userId, userForm));
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        this.userServices.delete(userId);
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/favorites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity favorites(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.userServices.get(userId).getFavorites());
    }

    @RequestMapping(value = "/{userId}/wallets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallets(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.userServices.get(userId).getWallets());
    }

    @RequestMapping(value = "/{userId}/alerts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alerts(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.userServices.get(userId).getAlerts());
    }

    @RequestMapping(value = "/{userId}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity settings(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.userServices.get(userId).getSettings());
    }

    @RequestMapping(value = "/{userId}/tokens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity tokens(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.userServices.get(userId).getTokens());
    }

    @RequestMapping(value = "/{userId}/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity logs(@PathVariable String token, @PathVariable int userId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.userServices.get(userId).getLogs());
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}/assets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity assets(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(wallet.getAssets());
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallet(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(wallet);
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alert(@PathVariable String token, @PathVariable int userId, @PathVariable int alertId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Alert alert = this.alertServices.get(alertId);
        assertEquals(user.getId(), alert.getUser().getId());

        return serializeSuccessResponse(alert);
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity setting(@PathVariable String token, @PathVariable int userId, @PathVariable int settingId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Setting setting = this.settingServices.get(settingId);
        assertEquals(user.getId(), setting.getUser().getId());

        return serializeSuccessResponse(setting);
    }

    @RequestMapping(value = "/{userId}/token/{tokenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity token(@PathVariable String token, @PathVariable int userId, @PathVariable int tokenId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Token tokenEntity = this.tokenServices.get(tokenId);
        assertEquals(user.getId(), tokenEntity.getUser().getId());

        return serializeSuccessResponse(tokenEntity);
    }

    @RequestMapping(value = "/{userId}/log/{logId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity log(@PathVariable String token, @PathVariable int userId, @PathVariable int logId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Log log = this.logServices.get(logId);
        assertEquals(user.getId(), log.getUser().getId());

        return serializeSuccessResponse(log);
    }

    @RequestMapping(value = "/{userId}/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity asset(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(this.assetServices.get(wallet.getId(), cryptocurrency.getId()));
    }

    @RequestMapping(value = "/{userId}/favorite/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addFavorite(@PathVariable String token, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.favoriteServices.insert(userId, cryptocurrencyId, new FavoriteForm()));
    }

    @RequestMapping(value = "/{userId}/wallet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addWallet(@PathVariable String token, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.walletServices.insert(deserialize(data, WalletForm.class)));
    }

    @RequestMapping(value = "/{userId}/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addAsset(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        try {
            // If the specified asset already exists : just update it
            final Asset asset = this.assetServices.get(walletId, cryptocurrencyId);
            final AssetForm assetForm = deserialize(data, AssetForm.class);
            assetForm.setAmount(asset.getAmount().add(assetForm.getAmount()));
            assetForm.setPurchasePrice(asset.getPurchasePrice().add(assetForm.getPurchasePrice()));

            return serializeSuccessResponse(this.assetServices.update(walletId, cryptocurrencyId, assetForm));
        } catch (UnknownEntityException e) {
            // Else : just create it
            return serializeSuccessResponse(this.assetServices.insert(walletId, cryptocurrencyId, deserialize(data, AssetForm.class)));
        }
    }

    @RequestMapping(value = "/{userId}/alert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addAlert(@PathVariable String token, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.alertServices.insert(deserialize(data, AlertForm.class)));
    }

    @RequestMapping(value = "/{userId}/setting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addSetting(@PathVariable String token, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        return serializeSuccessResponse(this.settingServices.insert(deserialize(data, SettingForm.class)));
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateWallet(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(this.walletServices.update(wallet.getId(), deserialize(data, WalletForm.class)));
    }

    @RequestMapping(value = "/{userId}/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateAsset(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(this.assetServices.update(wallet.getId(), cryptocurrency.getId(), deserialize(data, AssetForm.class)));
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateAlert(@PathVariable String token, @PathVariable int userId, @PathVariable int alertId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Alert alert = this.alertServices.get(alertId);
        assertEquals(user.getId(), alert.getUser().getId());

        return serializeSuccessResponse(this.alertServices.update(alert.getId(), deserialize(data, AlertForm.class)));
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateSetting(@PathVariable String token, @PathVariable int userId, @PathVariable int settingId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Setting setting = this.settingServices.get(settingId);
        assertEquals(user.getId(), setting.getUser().getId());

        return serializeSuccessResponse(this.settingServices.update(setting.getId(), deserialize(data, SettingForm.class)));
    }

    @RequestMapping(value = "/{userId}/favorite/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteFavorite(@PathVariable String token, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);
        this.favoriteServices.delete(userId, cryptocurrencyId);
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteWallet(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        assertEquals(user.getId(), wallet.getUser().getId());

        this.walletServices.delete(wallet.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteAsset(@PathVariable String token, @PathVariable int userId, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);
        assertEquals(user.getId(), wallet.getUser().getId());

        this.assetServices.delete(wallet.getId(), cryptocurrency.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteAlert(@PathVariable String token, @PathVariable int userId, @PathVariable int alertId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Alert alert = this.alertServices.get(alertId);
        assertEquals(user.getId(), alert.getUser().getId());

        this.alertServices.delete(alert.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteSetting(@PathVariable String token, @PathVariable int userId, @PathVariable int settingId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Setting setting = this.settingServices.get(settingId);
        assertEquals(user.getId(), setting.getUser().getId());

        this.settingServices.delete(setting.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/token/{tokenId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteToken(@PathVariable String token, @PathVariable int userId, @PathVariable int tokenId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(token, userId);

        final User user = this.userServices.get(userId);
        final Token tokenEntity = this.tokenServices.get(tokenId);
        assertEquals(user.getId(), tokenEntity.getUser().getId());

        this.tokenServices.delete(tokenEntity.getId());
        return serializeSuccessResponse();
    }

    private void assertEquals(int userId, int entityUserId) throws UnauthorizedUserException {
        if (userId != entityUserId) {
            throw new UnauthorizedUserException();
        }
    }
}