package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Wallet;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.WalletForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import fr.polytech.codev.backend.services.WalletSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/wallet")
public class AdministratorWalletsController extends AbstractController {

    @Autowired
    private WalletSqlDaoServices walletSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Wallet> wallets = this.walletSqlDaoServices.getAll();
        if (wallets == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(wallets)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Wallet wallet = this.walletSqlDaoServices.get(id);
        if (wallet == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(wallet)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final WalletForm walletForm = deserialize(data, WalletForm.class);

        final Wallet wallet = new Wallet();
        wallet.setName(walletForm.getName());
        wallet.setCreationDate(LocalDateTime.now());
        wallet.setLastUpdate(LocalDateTime.now());
        wallet.setUser(this.userSqlDaoServices.get(walletForm.getUserId()));

        validate(wallet);

        this.walletSqlDaoServices.insert(wallet);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(wallet)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Wallet wallet = this.walletSqlDaoServices.get(id);
        if (wallet == null) {
            throw new UnknownEntityException();
        }

        final WalletForm walletForm = deserialize(data, WalletForm.class);
        wallet.setName(walletForm.getName());
        wallet.setLastUpdate(LocalDateTime.now());

        validate(wallet);

        this.walletSqlDaoServices.update(wallet);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(wallet)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Wallet wallet = this.walletSqlDaoServices.get(id);
        if (wallet == null) {
            throw new UnknownEntityException();
        }

        this.walletSqlDaoServices.delete(wallet);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}