package fr.polytech.codev.backend.controllers.registered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.ExpiredTokenException;
import fr.polytech.codev.backend.exceptions.InvalidTokenException;
import fr.polytech.codev.backend.exceptions.UnauthorizedUserException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/registered/{tokenValue}/user")
public class RegisteredUsersController extends AbstractController {

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(user)));
    }
}