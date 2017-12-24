package fr.polytech.codev.backend.controllers.unregistered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AuthenticationForm;
import fr.polytech.codev.backend.forms.LogForm;
import fr.polytech.codev.backend.forms.TokenForm;
import fr.polytech.codev.backend.forms.UserForm;
import fr.polytech.codev.backend.services.impl.LogServices;
import fr.polytech.codev.backend.services.impl.TokenServices;
import fr.polytech.codev.backend.services.impl.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/unregistered/user")
public class UnregisteredUserController extends AbstractController {

    public static final boolean DEFAULT_IS_ADMINISTRATOR_VALUE = false;

    public static final boolean DEFAULT_IS_ENABLED_VALUE = true;

    @Autowired
    private UserServices userServices;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private LogServices logServices;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity subscribe(@RequestBody String data) throws InvalidEntityException {
        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setAdministrator(DEFAULT_IS_ADMINISTRATOR_VALUE);
        userForm.setEnabled(DEFAULT_IS_ENABLED_VALUE);

        return serializeSuccessResponse(this.userServices.insert(userForm));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity authenticate(HttpServletRequest request, @RequestBody String data) throws InvalidEntityException, UnknownEntityException {
        final User user = this.userServices.getByCredentials(deserialize(data, AuthenticationForm.class));

        final TokenForm tokenForm = new TokenForm();
        tokenForm.setEndDate(LocalDateTime.now().plusDays(DEFAULT_TOKEN_END_DATE_PLUS_DAY_VALUE));
        tokenForm.setUserId(user.getId());

        final LogForm logForm = new LogForm();
        logForm.setIpAddress(request.getRemoteAddr());
        logForm.setUserId(user.getId());

        this.logServices.insert(logForm);
        return serializeSuccessResponse(this.tokenServices.insert(tokenForm));
    }
}