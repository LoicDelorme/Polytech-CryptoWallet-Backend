package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Token;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.TokenForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.TokenSqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/token")
public class AdministratorTokensController extends AbstractController {

    @Autowired
    private TokenSqlDaoServices tokenSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Token> tokens = this.tokenSqlDaoServices.getAll();
        if (tokens == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(tokens)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Token token = this.tokenSqlDaoServices.get(id);
        if (token == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(token)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final TokenForm tokenForm = deserialize(data, TokenForm.class);

        final Token token = new Token();
        token.setValue(UUID.randomUUID().toString());
        token.setBeginDate(LocalDateTime.now());
        token.setEndDate(tokenForm.getEndDate());
        token.setCreationDate(LocalDateTime.now());
        token.setLastUpdate(LocalDateTime.now());
        token.setUser(this.userSqlDaoServices.get(tokenForm.getUserId()));

        validate(token);

        this.tokenSqlDaoServices.insert(token);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(token)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Token token = this.tokenSqlDaoServices.get(id);
        if (token == null) {
            throw new UnknownEntityException();
        }

        final TokenForm tokenForm = deserialize(data, TokenForm.class);
        token.setEndDate(tokenForm.getEndDate());
        token.setLastUpdate(LocalDateTime.now());

        validate(token);

        this.tokenSqlDaoServices.update(token);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(token)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Token token = this.tokenSqlDaoServices.get(id);
        if (token == null) {
            throw new UnknownEntityException();
        }

        this.tokenSqlDaoServices.delete(token);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}