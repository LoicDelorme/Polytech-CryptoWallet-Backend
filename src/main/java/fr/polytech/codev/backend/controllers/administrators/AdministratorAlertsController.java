package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.AlertForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.AlertSqlDaoServices;
import fr.polytech.codev.backend.services.AlertTypeSqlDaoServices;
import fr.polytech.codev.backend.services.CryptocurrencySqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/alert")
public class AdministratorAlertsController extends AbstractController {

    @Autowired
    private AlertSqlDaoServices alertSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @Autowired
    private AlertTypeSqlDaoServices alertTypeSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Alert> alerts = this.alertSqlDaoServices.getAll();
        if (alerts == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(alerts)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Alert alert = this.alertSqlDaoServices.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(alert)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final AlertForm alertForm = deserialize(data, AlertForm.class);

        final Alert alert = new Alert();
        alert.setThreshold(alertForm.getThreshold());
        alert.setOneShot(alertForm.isOneShot());
        alert.setActive(alertForm.isActive());
        alert.setCreationDate(LocalDateTime.now());
        alert.setLastUpdate(LocalDateTime.now());
        alert.setUser(this.userSqlDaoServices.get(alertForm.getUserId()));
        alert.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeSqlDaoServices.get(alertForm.getTypeId()));

        validate(alert);

        this.alertSqlDaoServices.insert(alert);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(alert)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Alert alert = this.alertSqlDaoServices.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        final AlertForm alertForm = deserialize(data, AlertForm.class);
        alert.setThreshold(alertForm.getThreshold());
        alert.setOneShot(alertForm.isOneShot());
        alert.setActive(alertForm.isActive());
        alert.setLastUpdate(LocalDateTime.now());
        alert.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeSqlDaoServices.get(alertForm.getTypeId()));

        validate(alert);

        this.alertSqlDaoServices.update(alert);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(alert)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Alert alert = this.alertSqlDaoServices.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        this.alertSqlDaoServices.delete(alert);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}