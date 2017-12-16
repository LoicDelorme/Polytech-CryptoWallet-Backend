package fr.polytech.codev.backend.services.controllers.implementations;

import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.CryptocurrencyForm;
import fr.polytech.codev.backend.services.controllers.AbstractControllerServices;
import fr.polytech.codev.backend.services.dao.implementations.CryptocurrencySqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class CryptocurrencyControllerServices extends AbstractControllerServices {

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    public List<Cryptocurrency> all() throws UnknownEntityException {
        final List<Cryptocurrency> cryptocurrencies = this.cryptocurrencySqlDaoServices.getAll();
        if (cryptocurrencies == null) {
            throw new UnknownEntityException();
        }

        return cryptocurrencies;
    }

    public Cryptocurrency get(int id) throws UnknownEntityException {
        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoServices.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        return cryptocurrency;
    }

    public Cryptocurrency insert(CryptocurrencyForm cryptocurrencyForm) throws InvalidEntityException {
        final Cryptocurrency cryptocurrency = new Cryptocurrency();
        cryptocurrency.setName(cryptocurrencyForm.getName());
        cryptocurrency.setSymbol(cryptocurrencyForm.getSymbol());
        cryptocurrency.setImageUrl(cryptocurrencyForm.getImageUrl());
        cryptocurrency.setBaseUrl(cryptocurrencyForm.getBaseUrl());
        cryptocurrency.setResourceUrl(cryptocurrencyForm.getResourceUrl());
        cryptocurrency.setCreationDate(LocalDateTime.now());
        cryptocurrency.setLastUpdate(LocalDateTime.now());

        validate(cryptocurrency);
        this.cryptocurrencySqlDaoServices.insert(cryptocurrency);

        return cryptocurrency;
    }

    public Cryptocurrency update(int id, CryptocurrencyForm cryptocurrencyForm) throws UnknownEntityException, InvalidEntityException {
        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoServices.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        cryptocurrency.setName(cryptocurrencyForm.getName());
        cryptocurrency.setSymbol(cryptocurrencyForm.getSymbol());
        cryptocurrency.setImageUrl(cryptocurrencyForm.getImageUrl());
        cryptocurrency.setBaseUrl(cryptocurrencyForm.getBaseUrl());
        cryptocurrency.setResourceUrl(cryptocurrencyForm.getResourceUrl());
        cryptocurrency.setLastUpdate(LocalDateTime.now());

        validate(cryptocurrency);
        this.cryptocurrencySqlDaoServices.update(cryptocurrency);

        return cryptocurrency;
    }

    public void delete(int id) throws UnknownEntityException {
        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoServices.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        this.cryptocurrencySqlDaoServices.delete(cryptocurrency);
    }
}