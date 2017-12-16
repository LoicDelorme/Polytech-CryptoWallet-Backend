package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Favorite;
import fr.polytech.codev.backend.entities.pks.FavoritePk;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.FavoriteForm;
import fr.polytech.codev.backend.services.AbstractServices;
import fr.polytech.codev.backend.repositories.sql.impl.CryptocurrencySqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.FavoriteSqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.UserSqlDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteServices extends AbstractServices {

    @Autowired
    private FavoriteSqlDaoRepository favoriteSqlDaoRepository;

    @Autowired
    private CryptocurrencySqlDaoRepository cryptocurrencySqlDaoRepository;

    @Autowired
    private UserSqlDaoRepository userSqlDaoRepository;

    public List<Favorite> all() throws UnknownEntityException {
        final List<Favorite> favorites = this.favoriteSqlDaoRepository.getAll();
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public Favorite get(int userId, int cryptocurrencyId) throws UnknownEntityException {
        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userSqlDaoRepository.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteSqlDaoRepository.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        return favorite;
    }

    public List<Favorite> getByUser(int userId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("user", this.userSqlDaoRepository.get(userId));

        final List<Favorite> favorites = this.favoriteSqlDaoRepository.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public List<Favorite> getByCryptocurrency(int cryptocurrencyId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        final List<Favorite> favorites = this.favoriteSqlDaoRepository.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public Favorite insert(int userId, int cryptocurrencyId, FavoriteForm favoriteForm) throws InvalidEntityException {
        final Favorite favorite = new Favorite();
        favorite.setUser(this.userSqlDaoRepository.get(userId));
        favorite.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        validate(favorite);
        this.favoriteSqlDaoRepository.insert(favorite);

        return favorite;
    }

    public void delete(int userId, int cryptocurrencyId) throws UnknownEntityException {
        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userSqlDaoRepository.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteSqlDaoRepository.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        this.favoriteSqlDaoRepository.delete(favorite);
    }
}