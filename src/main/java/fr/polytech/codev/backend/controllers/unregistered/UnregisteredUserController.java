package fr.polytech.codev.backend.controllers.unregistered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AuthenticationForm;
import fr.polytech.codev.backend.forms.TokenForm;
import fr.polytech.codev.backend.forms.UserForm;
import fr.polytech.codev.backend.services.impl.TokenServices;
import fr.polytech.codev.backend.services.impl.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/unregistered/user")
public class UnregisteredUserController extends AbstractController {

    public static final boolean DEFAULT_IS_ADMINISTRATOR_VALUE = false;

    public static final boolean DEFAULT_IS_ENABLED_VALUE = true;

    public static final int DEFAULT_TOKEN_END_DATE_PLUS_DAY_VALUE = 7;

    @Autowired
    private UserServices userServices;

    @Autowired
    private TokenServices tokenServices;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@RequestBody String data) throws InvalidEntityException {
        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setAdministrator(DEFAULT_IS_ADMINISTRATOR_VALUE);
        userForm.setEnabled(DEFAULT_IS_ENABLED_VALUE);

        return serializeSuccessResponse(this.userServices.insert(userForm));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity authenticate(@RequestBody String data) throws InvalidEntityException, UnknownEntityException {
        final AuthenticationForm authenticationForm = deserialize(data, AuthenticationForm.class);
        final User user = this.userServices.getByCredentials(authenticationForm.getEmail(), authenticationForm.getPassword());

        final TokenForm tokenForm = new TokenForm();
        tokenForm.setEndDate(LocalDateTime.now().plusDays(DEFAULT_TOKEN_END_DATE_PLUS_DAY_VALUE));
        tokenForm.setUserId(user.getId());

        return serializeSuccessResponse(this.tokenServices.insert(tokenForm));
    }
}