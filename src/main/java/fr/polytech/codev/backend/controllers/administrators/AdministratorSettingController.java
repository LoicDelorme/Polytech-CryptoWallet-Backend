package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.SettingForm;
import fr.polytech.codev.backend.services.impl.SettingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{token}/setting")
public class AdministratorSettingController extends AbstractController {

    @Autowired
    private SettingServices settingServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String token) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.settingServices.all());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.settingServices.get(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String token, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.settingServices.insert(deserialize(data, SettingForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String token, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        return serializeSuccessResponse(this.settingServices.update(id, deserialize(data, SettingForm.class)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String token, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(token);
        this.settingServices.delete(id);
        return serializeSuccessResponse();
    }
}