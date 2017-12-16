package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.entities.Favorite;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.entities.pks.FavoritePk;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.FavoriteForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteServices extends AbstractServices {

    @Autowired
    private DaoRepository<Favorite> favoriteDaoRepository;

    @Autowired
    private DaoRepository<Cryptocurrency> cryptocurrencyDaoRepository;

    @Autowired
    private DaoRepository<User> userDaoRepository;

    public List<Favorite> all() throws UnknownEntityException {
        final List<Favorite> favorites = this.favoriteDaoRepository.getAll();
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public Favorite get(int userId, int cryptocurrencyId) throws UnknownEntityException {
        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userDaoRepository.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencyDaoRepository.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteDaoRepository.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        return favorite;
    }

    public List<Favorite> getByUser(int userId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("user", this.userDaoRepository.get(userId));

        final List<Favorite> favorites = this.favoriteDaoRepository.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public List<Favorite> getByCryptocurrency(int cryptocurrencyId) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("cryptocurrency", this.cryptocurrencyDaoRepository.get(cryptocurrencyId));

        final List<Favorite> favorites = this.favoriteDaoRepository.filter(parameters);
        if (favorites == null) {
            throw new UnknownEntityException();
        }

        return favorites;
    }

    public Favorite insert(int userId, int cryptocurrencyId, FavoriteForm favoriteForm) throws InvalidEntityException {
        final Favorite favorite = new Favorite();
        favorite.setUser(this.userDaoRepository.get(userId));
        favorite.setCryptocurrency(this.cryptocurrencyDaoRepository.get(cryptocurrencyId));

        validate(favorite);
        this.favoriteDaoRepository.insert(favorite);

        return favorite;
    }

    public void delete(int userId, int cryptocurrencyId) throws UnknownEntityException {
        final FavoritePk favoritePk = new FavoritePk();
        favoritePk.setUser(this.userDaoRepository.get(userId));
        favoritePk.setCryptocurrency(this.cryptocurrencyDaoRepository.get(cryptocurrencyId));

        final Favorite favorite = this.favoriteDaoRepository.get(favoritePk);
        if (favorite == null) {
            throw new UnknownEntityException();
        }

        this.favoriteDaoRepository.delete(favorite);
    }
}