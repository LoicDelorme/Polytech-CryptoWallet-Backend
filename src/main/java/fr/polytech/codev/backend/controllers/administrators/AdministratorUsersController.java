package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.UserForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/user")
public class AdministratorUsersController extends AbstractController {

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<User> users = this.userSqlDaoServices.getAll();
        if (users == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(users)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final UserForm userForm = deserialize(data, UserForm.class);

        final User user = new User();
        user.setLastname(userForm.getLastname());
        user.setFirstname(userForm.getFirstname());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setEnabled(userForm.isEnabled());
        user.setAdministrator(userForm.isAdministrator());
        user.setCreationDate(LocalDateTime.now());
        user.setLastUpdate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());

        validate(user);

        this.userSqlDaoServices.insert(user);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(user)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        final UserForm userForm = deserialize(data, UserForm.class);
        user.setLastname(userForm.getLastname());
        user.setFirstname(userForm.getFirstname());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setEnabled(userForm.isEnabled());
        user.setAdministrator(userForm.isAdministrator());
        user.setLastUpdate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());

        validate(user);

        this.userSqlDaoServices.update(user);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(user)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        this.userSqlDaoServices.delete(user);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}