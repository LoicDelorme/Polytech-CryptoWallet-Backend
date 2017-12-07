package fr.polytech.codev.backend.controllers.unregistered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.CryptocurrencySqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/unregistered/cryptocurrency")
public class UnregisteredCryptocurrenciesController extends AbstractController {

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all() throws UnknownEntityException {
        final List<Cryptocurrency> cryptocurrencies = this.cryptocurrencySqlDaoServices.getAll();
        if (cryptocurrencies == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(cryptocurrencies)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable int id) throws UnknownEntityException {
        final Cryptocurrency cryptocurrency = this.cryptocurrencySqlDaoServices.get(id);
        if (cryptocurrency == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(cryptocurrency)));
    }
}