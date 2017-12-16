package fr.polytech.codev.backend.controllers.unregistered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.forms.UserForm;
import fr.polytech.codev.backend.services.impl.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/unregistered/user")
public class UnregisteredUserController extends AbstractController {

    public static final boolean DEFAULT_IS_ADMINISTRATOR_VALUE = false;

    public static final boolean DEFAULT_IS_ENABLED_VALUE = true;

    @Autowired
    private UserServices userServices;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@RequestBody String data) throws InvalidEntityException {
        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setAdministrator(DEFAULT_IS_ADMINISTRATOR_VALUE);
        userForm.setEnabled(DEFAULT_IS_ENABLED_VALUE);

        return serializeSuccessResponse(this.userServices.insert(userForm));
    }
}