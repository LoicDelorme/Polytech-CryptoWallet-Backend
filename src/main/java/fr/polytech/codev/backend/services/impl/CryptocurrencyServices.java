package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.CryptocurrencyForm;
import fr.polytech.codev.backend.services.AbstractServices;
import fr.polytech.codev.backend.repositories.sql.impl.CryptocurrencySqlDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class CryptocurrencyServices extends AbstractServices {

    @Autowired
    private CryptocurrencySqlDaoRepository cryptocurrencySqlDaoRepository;

    public List<Cryptocurrency> all() throws UnknownEntityException {
        final List<Cryptocurrency> cryptocurrencies = this.cryptocurrencySqlDaoRepository.getAll();
        if (cryptocurrencies == null) {
            throw new UnknownEntityException();
        }

        return cryptocurrencies;
    }

    public Cryptocurrency get(int id) throws UnknownEntityException {
        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoRepository.get(id);
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
        this.cryptocurrencySqlDaoRepository.insert(cryptocurrency);

        return cryptocurrency;
    }

    public Cryptocurrency update(int id, CryptocurrencyForm cryptocurrencyForm) throws UnknownEntityException, InvalidEntityException {
        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoRepository.get(id);
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
        this.cryptocurrencySqlDaoRepository.update(cryptocurrency);

        return cryptocurrency;
    }

    public void delete(int id) throws UnknownEntityException {
        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoRepository.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        this.cryptocurrencySqlDaoRepository.delete(cryptocurrency);
    }
}