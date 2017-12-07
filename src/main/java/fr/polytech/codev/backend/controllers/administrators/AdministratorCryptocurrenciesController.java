package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.CryptocurrencyForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.CryptocurrencySqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/cryptocurrency")
public class AdministratorCryptocurrenciesController extends AbstractController {

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Cryptocurrency> cryptocurrencies = this.cryptocurrencySqlDaoServices.getAll();
        if (cryptocurrencies == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(cryptocurrencies)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoServices.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(cryptocurrency)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final CryptocurrencyForm cryptocurrencyForm = deserialize(data, CryptocurrencyForm.class);

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
        return ResponseEntity.ok().body(serialize(new SuccessResponse(cryptocurrency)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoServices.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        final CryptocurrencyForm cryptocurrencyForm = deserialize(data, CryptocurrencyForm.class);
        cryptocurrency.setName(cryptocurrencyForm.getName());
        cryptocurrency.setSymbol(cryptocurrencyForm.getSymbol());
        cryptocurrency.setImageUrl(cryptocurrencyForm.getImageUrl());
        cryptocurrency.setBaseUrl(cryptocurrencyForm.getBaseUrl());
        cryptocurrency.setResourceUrl(cryptocurrencyForm.getResourceUrl());
        cryptocurrency.setLastUpdate(LocalDateTime.now());

        validate(cryptocurrency);

        this.cryptocurrencySqlDaoServices.update(cryptocurrency);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(cryptocurrency)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoServices.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        this.cryptocurrencySqlDaoServices.delete(cryptocurrency);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}