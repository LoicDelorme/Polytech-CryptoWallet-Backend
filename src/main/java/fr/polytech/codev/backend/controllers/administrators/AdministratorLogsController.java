package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.LogForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.LogSqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/log")
public class AdministratorLogsController extends AbstractController {

    @Autowired
    private LogSqlDaoServices logSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Log> logs = this.logSqlDaoServices.getAll();
        if (logs == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(logs)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Log log = this.logSqlDaoServices.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(log)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final LogForm logForm = deserialize(data, LogForm.class);

        final Log log = new Log();
        log.setIpAddress(logForm.getIpAddress());
        log.setCreationDate(LocalDateTime.now());
        log.setLastUpdate(LocalDateTime.now());
        log.setUser(this.userSqlDaoServices.get(logForm.getUserId()));

        validate(log);

        this.logSqlDaoServices.insert(log);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(log)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Log log = this.logSqlDaoServices.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        final LogForm logForm = deserialize(data, LogForm.class);
        log.setIpAddress(logForm.getIpAddress());
        log.setLastUpdate(LocalDateTime.now());

        validate(log);

        this.logSqlDaoServices.update(log);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(log)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Log log = this.logSqlDaoServices.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        this.logSqlDaoServices.delete(log);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}