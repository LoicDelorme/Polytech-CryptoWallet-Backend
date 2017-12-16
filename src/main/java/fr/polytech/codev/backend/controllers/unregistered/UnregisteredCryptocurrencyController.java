package fr.polytech.codev.backend.controllers.unregistered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.services.controllers.implementations.CryptocurrencyControllerServices;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/unregistered/cryptocurrency")
public class UnregisteredCryptocurrencyController extends AbstractController {

    @Autowired
    private CryptocurrencyControllerServices cryptocurrencyControllerServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all() throws UnknownEntityException {
        return serializeSuccessResponse(this.cryptocurrencyControllerServices.all());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable int id) throws UnknownEntityException {
        return serializeSuccessResponse(this.cryptocurrencyControllerServices.get(id));
    }
}