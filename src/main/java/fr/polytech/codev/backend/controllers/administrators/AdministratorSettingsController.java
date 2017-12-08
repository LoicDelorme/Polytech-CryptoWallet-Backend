package fr.polytech.codev.backend.controllers.administrators;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.SettingForm;
import fr.polytech.codev.backend.responses.SuccessResponse;
import fr.polytech.codev.backend.services.SettingSqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/administrator/{tokenValue}/setting")
public class AdministratorSettingsController extends AbstractController {

    @Autowired
    private SettingSqlDaoServices settingSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity all(@PathVariable String tokenValue) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final List<Setting> settings = this.settingSqlDaoServices.getAll();
        if (settings == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(settings)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Setting setting = this.settingSqlDaoServices.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        return ResponseEntity.ok().body(serialize(new SuccessResponse(setting)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity insert(@PathVariable String tokenValue, @RequestBody String data) throws InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final SettingForm settingForm = deserialize(data, SettingForm.class);

        final Setting setting = new Setting();
        setting.setName(settingForm.getName());
        setting.setTheme(settingForm.getTheme());
        setting.setCreationDate(LocalDateTime.now());
        setting.setLastUpdate(LocalDateTime.now());
        setting.setUser(this.userSqlDaoServices.get(settingForm.getUserId()));

        validate(setting);

        this.settingSqlDaoServices.insert(setting);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(setting)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Setting setting = this.settingSqlDaoServices.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        final SettingForm settingForm = deserialize(data, SettingForm.class);
        setting.setName(settingForm.getName());
        setting.setTheme(settingForm.getTheme());
        setting.setLastUpdate(LocalDateTime.now());

        validate(setting);

        this.settingSqlDaoServices.update(setting);
        return ResponseEntity.ok().body(serialize(new SuccessResponse(setting)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsAdministrator(tokenValue);

        final Setting setting = this.settingSqlDaoServices.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        this.settingSqlDaoServices.delete(setting);
        return ResponseEntity.ok().body(serialize(new SuccessResponse()));
    }
}