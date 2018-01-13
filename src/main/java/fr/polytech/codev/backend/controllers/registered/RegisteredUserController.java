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

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/registered/{token}/user")
public class RegisteredUserController extends AbstractController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private FavoriteServices favoriteServices;

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
    private DeviceServices deviceServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String token, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);

        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setAdministrator(user.isAdministrator());
        userForm.setEnabled(user.isEnabled());

        return serializeSuccessResponse("The user was successfully updated!", this.userServices.update(user.getId(), userForm));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        this.userServices.delete(user.getId());

        return serializeSuccessResponse("The user was successfully deleted!");
    }

    @RequestMapping(value = "/favorites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity favorites(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user.getFavorites());
    }

    @RequestMapping(value = "/wallets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallets(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user.getWallets());
    }

    @RequestMapping(value = "/alerts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alerts(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user.getAlerts());
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity settings(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user.getSetting());
    }

    @RequestMapping(value = "/tokens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity tokens(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user.getTokens());
    }

    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity logs(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user.getLogs());
    }

    @RequestMapping(value = "/devices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity devices(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse(user.getDevices());
    }

    @RequestMapping(value = "/wallet/{id}/assets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity assets(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == id).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse(wallets.get(0).getAssets());
    }

    @RequestMapping(value = "/wallet/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallet(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == id).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse(wallets.get(0));
    }

    @RequestMapping(value = "/alert/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alert(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Alert> alerts = user.getAlerts().stream().filter(alert -> alert.getId() == id).collect(Collectors.toList());
        if (alerts.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse(alerts.get(0));
    }

    @RequestMapping(value = "/token/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity token(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Token> tokens = user.getTokens().stream().filter(token_ -> token_.getId() == id).collect(Collectors.toList());
        if (tokens.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse(tokens.get(0));
    }

    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity log(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Log> logs = user.getLogs().stream().filter(log -> log.getId() == id).collect(Collectors.toList());
        if (logs.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse(logs.get(0));
    }

    @RequestMapping(value = "/device/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity device(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Device> devices = user.getDevices().stream().filter(device -> device.getId() == id).collect(Collectors.toList());
        if (devices.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse(devices.get(0));
    }

    @RequestMapping(value = "/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity asset(@PathVariable String token, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == walletId).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse(this.assetServices.get(walletId, cryptocurrencyId));
    }

    @RequestMapping(value = "/favorite/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addFavorite(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse("The favorite was successfully inserted!", this.favoriteServices.insert(user.getId(), id, new FavoriteForm()));
    }

    @RequestMapping(value = "/wallet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addWallet(@PathVariable String token, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);

        final WalletForm walletForm = deserialize(data, WalletForm.class);
        walletForm.setUserId(user.getId());

        return serializeSuccessResponse("The wallet was successfully inserted!", this.walletServices.insert(walletForm));
    }

    @RequestMapping(value = "/alert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addAlert(@PathVariable String token, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);

        final AlertForm alertForm = deserialize(data, AlertForm.class);
        alertForm.setUserId(user.getId());

        return serializeSuccessResponse("The alert was successfully inserted!", this.alertServices.insert(alertForm));
    }

    @RequestMapping(value = "/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addAsset(@PathVariable String token, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == walletId).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        try {
            // If the specified asset already exists : just update it
            final Asset asset = this.assetServices.get(walletId, cryptocurrencyId);

            final AssetForm assetForm = deserialize(data, AssetForm.class);
            assetForm.setAmount(asset.getAmount().add(assetForm.getAmount()));
            assetForm.setPurchasePrice(asset.getPurchasePrice().add(assetForm.getPurchasePrice()));

            return serializeSuccessResponse(this.assetServices.update(walletId, cryptocurrencyId, assetForm));
        } catch (UnknownEntityException e) {
            return serializeSuccessResponse(this.assetServices.insert(walletId, cryptocurrencyId, deserialize(data, AssetForm.class)));
        }
    }

    @RequestMapping(value = "/wallet/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateWallet(@PathVariable String token, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == id).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse("The wallet was successfully updated!", this.walletServices.update(id, deserialize(data, WalletForm.class)));
    }

    @RequestMapping(value = "/alert/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateAlert(@PathVariable String token, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Alert> alerts = user.getAlerts().stream().filter(alert -> alert.getId() == id).collect(Collectors.toList());
        if (alerts.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse("The alert was successfully updated!", this.alertServices.update(id, deserialize(data, AlertForm.class)));
    }

    @RequestMapping(value = "/settings", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateSettings(@PathVariable String token, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        return serializeSuccessResponse("The setting was successfully updated!", this.settingServices.update(user.getSetting().getId(), deserialize(data, SettingForm.class)));
    }

    @RequestMapping(value = "/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateAsset(@PathVariable String token, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == walletId).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        return serializeSuccessResponse("The asset was successfully updated!", this.assetServices.update(walletId, cryptocurrencyId, deserialize(data, AssetForm.class)));
    }

    @RequestMapping(value = "/favorite/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteFavorite(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        this.favoriteServices.delete(user.getId(), id);

        return serializeSuccessResponse("The favorite was successfully deleted!");
    }

    @RequestMapping(value = "/wallet/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteWallet(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == id).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        this.walletServices.delete(id);
        return serializeSuccessResponse("The wallet was successfully deleted!");
    }

    @RequestMapping(value = "/alert/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteAlert(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Alert> alerts = user.getAlerts().stream().filter(alert -> alert.getId() == id).collect(Collectors.toList());
        if (alerts.isEmpty()) {
            throw new UnknownEntityException();
        }

        this.alertServices.delete(id);
        return serializeSuccessResponse("The alert was successfully deleted!");
    }

    @RequestMapping(value = "/token/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteToken(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Token> tokens = user.getTokens().stream().filter(token_ -> token_.getId() == id).collect(Collectors.toList());
        if (tokens.isEmpty()) {
            throw new UnknownEntityException();
        }

        this.tokenServices.delete(id);
        return serializeSuccessResponse("The alert was successfully deleted!");
    }

    @RequestMapping(value = "/device/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteDevice(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Device> devices = user.getDevices().stream().filter(device -> device.getId() == id).collect(Collectors.toList());
        if (devices.isEmpty()) {
            throw new UnknownEntityException();
        }

        this.deviceServices.delete(id);
        return serializeSuccessResponse("The device was successfully deleted!");
    }

    @RequestMapping(value = "/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteAsset(@PathVariable String token, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        final User user = assertIsUser(token);
        final List<Wallet> wallets = user.getWallets().stream().filter(wallet -> wallet.getId() == walletId).collect(Collectors.toList());
        if (wallets.isEmpty()) {
            throw new UnknownEntityException();
        }

        this.assetServices.delete(walletId, cryptocurrencyId);
        return serializeSuccessResponse("The asset was successfully deleted!");
    }
}