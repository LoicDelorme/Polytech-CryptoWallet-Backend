package fr.polytech.codev.backend.controllers.registered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.services.controllers.implementations.AlertTypeControllerServices;
import fr.polytech.codev.backend.exceptions.ExpiredTokenException;
import fr.polytech.codev.backend.exceptions.InvalidTokenException;
import fr.polytech.codev.backend.exceptions.UnauthorizedUserException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/registered/{tokenValue}/alert-type")
public class RegisteredAlertTypeController extends AbstractController {

    @Autowired
    private AlertTypeControllerServices alertTypeControllerServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertIsUser(tokenValue);
        return serializeSuccessResponse(this.alertTypeControllerServices.all());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertIsUser(tokenValue);
        return serializeSuccessResponse(this.alertTypeControllerServices.get(id));
    }
}