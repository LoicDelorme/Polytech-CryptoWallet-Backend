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
    public ResponseEntity all(@PathVariable("token") String token) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.all());
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable("token") String token, @PathVariable("userId") int userId, @PathVariable("cryptocurrencyId") int cryptocurrencyId) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.get(userId, cryptocurrencyId));
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByUser(@PathVariable("token") String token, @PathVariable("id") int id) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.getByUser(id));
    }

    @RequestMapping(value = "cryptocurrency/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByCryptocurrency(@PathVariable("token") String token, @PathVariable("id") int id) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.favoriteServices.getByCryptocurrency(id));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable("token") String token, @PathVariable("userId") int userId, @PathVariable("cryptocurrencyId") int cryptocurrencyId, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The favorite was successfully inserted!", this.favoriteServices.insert(userId, cryptocurrencyId, new FavoriteForm()));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable("token") String token, @PathVariable("userId") int userId, @PathVariable("cryptocurrencyId") int cryptocurrencyId, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The favorite was successfully updated!", this.favoriteServices.update(userId, cryptocurrencyId, new FavoriteForm()));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable("token") String token, @PathVariable("userId") int userId, @PathVariable("cryptocurrencyId") int cryptocurrencyId) throws InvalidTokenException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        this.favoriteServices.delete(userId, cryptocurrencyId);
        return serializeSuccessResponse("The favorite was successfully deleted!");
    }
}