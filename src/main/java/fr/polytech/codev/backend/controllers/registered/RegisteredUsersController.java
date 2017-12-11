package fr.polytech.codev.backend.controllers.registered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.controllers.services.*;
import fr.polytech.codev.backend.entities.*;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/registered/{tokenValue}/user")
public class RegisteredUsersController extends AbstractController {

    @Autowired
    private UserControllerServices userControllerServices;

    @Autowired
    private WalletControllerServices walletControllerServices;

    @Autowired
    private AlertControllerServices alertControllerServices;

    @Autowired
    private SettingControllerServices settingControllerServices;

    @Autowired
    private TokenControllerServices tokenControllerServices;

    @Autowired
    private LogControllerServices logControllerServices;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userControllerServices.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setAdministrator(false);
        userForm.setEnabled(true);

        return serializeSuccessResponse(this.userControllerServices.update(id, userForm));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        this.userControllerServices.delete(id);
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{id}/favorites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity favorites(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userControllerServices.get(id).getFavorites());
    }

    @RequestMapping(value = "/{id}/wallets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallets(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userControllerServices.get(id).getWallets());
    }

    @RequestMapping(value = "/{id}/alerts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alerts(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userControllerServices.get(id).getAlerts());
    }

    @RequestMapping(value = "/{id}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity settings(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userControllerServices.get(id).getSettings());
    }

    @RequestMapping(value = "/{id}/tokens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity tokens(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userControllerServices.get(id).getTokens());
    }

    @RequestMapping(value = "/{id}/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity logs(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userControllerServices.get(id).getLogs());
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallet(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userControllerServices.get(userId);
        final Wallet wallet = this.walletControllerServices.get(walletId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(wallet);
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alert(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int alertId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userControllerServices.get(userId);
        final Alert alert = this.alertControllerServices.get(alertId);
        assertEquals(user.getId(), alert.getUser().getId());

        return serializeSuccessResponse(alert);
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity setting(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int settingId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userControllerServices.get(userId);
        final Setting setting = this.settingControllerServices.get(settingId);
        assertEquals(user.getId(), setting.getUser().getId());

        return serializeSuccessResponse(setting);
    }

    @RequestMapping(value = "/{userId}/token/{tokenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity token(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int tokenId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userControllerServices.get(userId);
        final Token token = this.tokenControllerServices.get(tokenId);
        assertEquals(user.getId(), token.getUser().getId());

        return serializeSuccessResponse(token);
    }

    @RequestMapping(value = "/{userId}/log/{logId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity log(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int logId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userControllerServices.get(userId);
        final Log log = this.logControllerServices.get(logId);
        assertEquals(user.getId(), log.getUser().getId());

        return serializeSuccessResponse(log);
    }

    private void assertEquals(int userId, int entityUserId) throws UnauthorizedUserException {
        if (userId != entityUserId) {
            throw new UnauthorizedUserException();
        }
    }
}