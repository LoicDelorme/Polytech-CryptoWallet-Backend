package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.services.impl.TokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{token}/token")
public class AdministratorTokenController extends AbstractController {

    @Autowired
    private TokenServices tokenServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String token) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.tokenServices.all());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.tokenServices.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        this.tokenServices.delete(id);
        return serializeSuccessResponse("The token was successfully deleted!");
    }
}