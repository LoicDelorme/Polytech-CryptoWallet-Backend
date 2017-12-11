package fr.polytech.codev.backend.controllers.services;

import fr.polytech.codev.backend.entities.Favorite;
import fr.polytech.codev.backend.entities.pks.FavoritePk;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.FavoriteForm;
import fr.polytech.codev.backend.services.CryptocurrencySqlDaoServices;
import fr.polytech.codev.backend.services.FavoriteSqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteControllerServices extends AbstractControllerServices {

    @Autowired
    private FavoriteSqlDaoServices favoriteSqlDaoServices;

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    public List<Favorite> all() throws UnknownEntityException {
        final List<Favorite> favorites = this.favoriteSqlDaoServices.getAll();
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public Favorite get(int userId, int cryptocurrencyId) throws UnknownEntityException {
        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userSqlDaoServices.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteSqlDaoServices.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        return favorite;
    }

    public List<Favorite> getByUser(int userId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("user", this.userSqlDaoServices.get(userId));

        final List<Favorite> favorites = this.favoriteSqlDaoServices.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public List<Favorite> getByCryptocurrency(int cryptocurrencyId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final List<Favorite> favorites = this.favoriteSqlDaoServices.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public Favorite insert(int userId, int cryptocurrencyId, FavoriteForm favoriteForm) throws InvalidEntityException {
        final Favorite favorite = new Favorite();
        favorite.setUser(this.userSqlDaoServices.get(userId));
        favorite.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        validate(favorite);
        this.favoriteSqlDaoServices.insert(favorite);

        return favorite;
    }

    public void delete(int userId, int cryptocurrencyId) throws UnknownEntityException {
        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userSqlDaoServices.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteSqlDaoServices.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        this.favoriteSqlDaoServices.delete(favorite);
    }
}