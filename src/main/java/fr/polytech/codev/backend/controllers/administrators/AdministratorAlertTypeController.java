package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.services.impl.AlertTypeServices;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.AlertTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/alert-type")
public class AdministratorAlertTypeController extends AbstractController {

    @Autowired
    private AlertTypeServices alertTypeServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.alertTypeServices.all());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.alertTypeServices.get(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.alertTypeServices.insert(deserialize(data, AlertTypeForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        return serializeSuccessResponse(this.alertTypeServices.update(id, deserialize(data, AlertTypeForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);
        this.alertTypeServices.delete(id);
        return serializeSuccessResponse();
    }
}