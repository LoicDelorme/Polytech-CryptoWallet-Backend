package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.CryptocurrencyForm;
import fr.polytech.codev.backend.services.impl.CryptocurrencyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{token}/cryptocurrency")
public class AdministratorCryptocurrencyController extends AbstractController {

    @Autowired
    private CryptocurrencyServices cryptocurrencyServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String token) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.cryptocurrencyServices.all());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.cryptocurrencyServices.get(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String token, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The cryptocurrency was successfully inserted!", this.cryptocurrencyServices.insert(deserialize(data, CryptocurrencyForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String token, @PathVariable int id, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The cryptocurrency was successfully updated!", this.cryptocurrencyServices.update(id, deserialize(data, CryptocurrencyForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        this.cryptocurrencyServices.delete(id);
        return serializeSuccessResponse("The cryptocurrency was successfully deleted!");
    }
}