package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Currency;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.CurrencyForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class CurrencyServices extends AbstractServices {

    @Autowired
    private DaoRepository<Currency> currencyDaoRepository;

    public List<Currency> all() throws UnknownEntityException {
        final List<Currency> currencies = this.currencyDaoRepository.getAll();
        if (currencies == null) {
            throw new UnknownEntityException();
        }

        return currencies;
    }

    public Currency get(int id) throws UnknownEntityException {
        final Currency currency = this.currencyDaoRepository.get(id);
        if (currency == null) {
            throw new UnknownEntityException();
        }

        return currency;
    }

    public Currency insert(CurrencyForm currencyForm) throws InvalidEntityException {
        final Currency currency = new Currency();
        currency.setName(currencyForm.getName());
        currency.setSymbol(currencyForm.getSymbol());
        currency.setCreationDate(LocalDateTime.now());
        currency.setLastUpdate(LocalDateTime.now());

        validate(currency);
        this.currencyDaoRepository.insert(currency);

        return currency;
    }

    public Currency update(int id, CurrencyForm currencyForm) throws UnknownEntityException, InvalidEntityException {
        final Currency currency = get(id);
        currency.setName(currencyForm.getName());
        currency.setSymbol(currencyForm.getSymbol());
        currency.setLastUpdate(LocalDateTime.now());

        validate(currency);
        this.currencyDaoRepository.update(currency);

        return currency;
    }

    public void delete(int id) throws UnknownEntityException {
        this.currencyDaoRepository.delete(get(id));
    }
}