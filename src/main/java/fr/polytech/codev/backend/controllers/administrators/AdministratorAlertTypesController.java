package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.AlertType;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.AlertTypeForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.AlertTypeSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/alert-type")
public class AdministratorAlertTypesController extends AbstractController {

    @Autowired
    private AlertTypeSqlDaoServices alertTypeSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<AlertType> alertTypes = this.alertTypeSqlDaoServices.getAll();
        if (alertTypes == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(alertTypes)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AlertType alertType = this.alertTypeSqlDaoServices.get(id);
        if (alertType == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(alertType)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AlertTypeForm alertTypeForm = deserialize(data, AlertTypeForm.class);

        final AlertType alertType = new AlertType();
        alertType.setName(alertTypeForm.getName());
        alertType.setCreationDate(LocalDateTime.now());
        alertType.setLastUpdate(LocalDateTime.now());

        validate(alertType);

        this.alertTypeSqlDaoServices.insert(alertType);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(alertType)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AlertType alertType = this.alertTypeSqlDaoServices.get(id);
        if (alertType == null) {
            throw new UnknownEntityException();
        }

        final AlertTypeForm alertTypeForm = deserialize(data, AlertTypeForm.class);
        alertType.setName(alertTypeForm.getName());
        alertType.setLastUpdate(LocalDateTime.now());

        validate(alertType);

        this.alertTypeSqlDaoServices.update(alertType);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(alertType)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AlertType alertType = this.alertTypeSqlDaoServices.get(id);
        if (alertType == null) {
            throw new UnknownEntityException();
        }

        this.alertTypeSqlDaoServices.delete(alertType);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}