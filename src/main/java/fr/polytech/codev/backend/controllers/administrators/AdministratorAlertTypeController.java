package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.AlertTypeForm;
import fr.polytech.codev.backend.services.impl.AlertTypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{token}/alert-type")
public class AdministratorAlertTypeController extends AbstractController {

    @Autowired
    private AlertTypeServices alertTypeServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String token) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.alertTypeServices.all());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.alertTypeServices.get(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String token, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The alert type was successfully inserted!", this.alertTypeServices.insert(deserialize(data, AlertTypeForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String token, @PathVariable int id, @RequestBody String data) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse("The alert type was successfully updated!", this.alertTypeServices.update(id, deserialize(data, AlertTypeForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String token, @PathVariable int id) throws InvalidTokenException, InvalidEntityException, ExpiredTokenException, UnknownEntityException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        this.alertTypeServices.delete(id);
        return serializeSuccessResponse("The alert type was successfully deleted!");
    }
}