package fr.polytech.codev.backend.controllers.registered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.*;
import fr.polytech.codev.backend.exceptions.ExpiredTokenException;
import fr.polytech.codev.backend.exceptions.InvalidTokenException;
import fr.polytech.codev.backend.exceptions.UnauthorizedUserException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/registered/{tokenValue}/user")
public class RegisteredUsersController extends AbstractController {

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @Autowired
    private WalletSqlDaoServices walletSqlDaoServices;

    @Autowired
    private AlertSqlDaoServices alertSqlDaoServices;

    @Autowired
    private SettingSqlDaoServices settingSqlDaoServices;

    @Autowired
    private TokenSqlDaoServices tokenSqlDaoServices;

    @Autowired
    private LogSqlDaoServices logSqlDaoServices;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user)));
    }

    @RequestMapping(value = "/{id}/favorites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity favorites(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user.getFavorites())));
    }

    @RequestMapping(value = "/{id}/wallets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallets(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user.getWallets())));
    }

    @RequestMapping(value = "/{id}/alerts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alerts(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user.getAlerts())));
    }

    @RequestMapping(value = "/{id}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity settings(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user.getSettings())));
    }

    @RequestMapping(value = "/{id}/tokens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity tokens(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user.getTokens())));
    }

    @RequestMapping(value = "/{id}/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity logs(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user.getLogs())));
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallet(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userSqlDaoServices.get(userId);
        if (user == null) {
            throw new UnknownEntityException();
        }

        final Wallet wallet = this.walletSqlDaoServices.get(walletId);
        if (wallet == null) {
            throw new UnknownEntityException();
        }

        if (userId != wallet.getUser().getId()) {
            throw new UnauthorizedUserException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(wallet)));
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alert(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int alertId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userSqlDaoServices.get(userId);
        if (user == null) {
            throw new UnknownEntityException();
        }

        final Alert alert = this.alertSqlDaoServices.get(alertId);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        if (userId != alert.getUser().getId()) {
            throw new UnauthorizedUserException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(alert)));
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity setting(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int settingId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userSqlDaoServices.get(userId);
        if (user == null) {
            throw new UnknownEntityException();
        }

        final Setting setting = this.settingSqlDaoServices.get(settingId);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        if (userId != setting.getUser().getId()) {
            throw new UnauthorizedUserException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(setting)));
    }

    @RequestMapping(value = "/{userId}/token/{tokenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity token(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int tokenId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userSqlDaoServices.get(userId);
        if (user == null) {
            throw new UnknownEntityException();
        }

        final Token token = this.tokenSqlDaoServices.get(tokenId);
        if (token == null) {
            throw new UnknownEntityException();
        }

        if (userId != token.getUser().getId()) {
            throw new UnauthorizedUserException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(token)));
    }

    @RequestMapping(value = "/{userId}/log/{logId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity log(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int logId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userSqlDaoServices.get(userId);
        if (user == null) {
            throw new UnknownEntityException();
        }

        final Log log = this.logSqlDaoServices.get(logId);
        if (log == null) {
            throw new UnknownEntityException();
        }

        if (userId != log.getUser().getId()) {
            throw new UnauthorizedUserException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(log)));
    }
}