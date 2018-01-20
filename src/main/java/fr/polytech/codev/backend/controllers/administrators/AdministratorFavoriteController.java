package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.FavoriteForm;
import fr.polytech.codev.backend.services.impl.FavoriteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{token}/favorite")
public class AdministratorFavoriteController extends AbstractController {

    @Autowired
    private FavoriteServices favoriteServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String token) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.all());
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String token, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.get(userId, cryptocurrencyId));
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByUser(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.getByUser(id));
    }

    @RequestMapping(value = "cryptocurrency/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByCryptocurrency(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.getByCryptocurrency(id));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String token, @PathVariable int userId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The favorite was successfully inserted!", this.favoriteServices.insert(userId, cryptocurrencyId, new FavoriteForm()));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String token, @PathVariable int userId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The favorite was successfully updated!", this.favoriteServices.update(userId, cryptocurrencyId, new FavoriteForm()));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String token, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        this.favoriteServices.delete(userId, cryptocurrencyId);
        return serializeSuccessResponse("The favorite was successfully deleted!");
    }
}