package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.services.impl.FavoriteServices;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.FavoriteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/favorite")
public class AdministratorFavoriteController extends AbstractController {

    @Autowired
    private FavoriteServices favoriteServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.favoriteServices.all());
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.favoriteServices.get(userId, cryptocurrencyId));
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByUser(@PathVariable String tokenValue, @PathVariable int userId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.favoriteServices.getByUser(userId));
    }

    @RequestMapping(value = "cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByCryptocurrency(@PathVariable String tokenValue, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.favoriteServices.getByCryptocurrency(cryptocurrencyId));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.favoriteServices.insert(userId, cryptocurrencyId, deserialize(data, FavoriteForm.class)));
    }

    @RequestMapping(value = "user/{userId}/cryptocurrency{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        this.favoriteServices.delete(userId, cryptocurrencyId);
        return serializeSuccessResponse();
    }
}