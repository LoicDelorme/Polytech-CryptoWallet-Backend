package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Favorite;
import fr.polytech.codev.backend.entities.pks.FavoritePk;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.FavoriteForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.CryptocurrencySqlDaoServices;
import fr.polytech.codev.backend.services.FavoriteSqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/favorite")
public class AdministratorFavoritesController extends AbstractController {

    @Autowired
    private FavoriteSqlDaoServices favoriteSqlDaoServices;

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Favorite> favorites = this.favoriteSqlDaoServices.getAll();
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(favorites)));
    }

    @RequestMapping(value = "/{userId}/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userSqlDaoServices.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteSqlDaoServices.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(favorite)));
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByUser(@PathVariable String tokenValue, @PathVariable int userId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("user", this.userSqlDaoServices.get(userId));

        final List<Favorite> favorites = this.favoriteSqlDaoServices.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(favorites)));
    }

    @RequestMapping(value = "cryptocurrency/{cryptocurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getByCryptocurrency(@PathVariable String tokenValue, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final List<Favorite> favorites = this.favoriteSqlDaoServices.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(favorites)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final FavoriteForm favoriteForm = deserialize(data, FavoriteForm.class);

        final Favorite favorite = new Favorite();
        favorite.setUser(this.userSqlDaoServices.get(favoriteForm.getUserId()));
        favorite.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(favoriteForm.getCryptocurrencyId()));

        validate(favorite);

        this.favoriteSqlDaoServices.insert(favorite);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(favorite)));
    }

    @RequestMapping(value = "/{userId}/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userSqlDaoServices.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteSqlDaoServices.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        this.favoriteSqlDaoServices.delete(favorite);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}