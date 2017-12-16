package fr.polytech.codev.backend.services.controllers.implementations;

import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.SettingForm;
import fr.polytech.codev.backend.services.controllers.AbstractControllerServices;
import fr.polytech.codev.backend.services.dao.implementations.SettingSqlDaoServices;
import fr.polytech.codev.backend.services.dao.implementations.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class SettingControllerServices extends AbstractControllerServices {

    @Autowired
    private SettingSqlDaoServices settingSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    public List<Setting> all() throws UnknownEntityException {
        final List<Setting> settings = this.settingSqlDaoServices.getAll();
        if (settings == null) {
            throw new UnknownEntityException();
        }

        return settings;
    }

    public Setting get(int id) throws UnknownEntityException {
        final Setting setting = this.settingSqlDaoServices.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        return setting;
    }

    public Setting insert(SettingForm settingForm) throws InvalidEntityException {
        final Setting setting = new Setting();
        setting.setName(settingForm.getName());
        setting.setTheme(settingForm.getTheme());
        setting.setCreationDate(LocalDateTime.now());
        setting.setLastUpdate(LocalDateTime.now());
        setting.setUser(this.userSqlDaoServices.get(settingForm.getUserId()));

        validate(setting);
        this.settingSqlDaoServices.insert(setting);

        return setting;
    }

    public Setting update(int id, SettingForm settingForm) throws UnknownEntityException, InvalidEntityException {
        final Setting setting = this.settingSqlDaoServices.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        setting.setName(settingForm.getName());
        setting.setTheme(settingForm.getTheme());
        setting.setLastUpdate(LocalDateTime.now());

        validate(setting);
        this.settingSqlDaoServices.update(setting);

        return setting;
    }

    public void delete(int id) throws UnknownEntityException {
        final Setting setting = this.settingSqlDaoServices.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        this.settingSqlDaoServices.delete(setting);
    }
}