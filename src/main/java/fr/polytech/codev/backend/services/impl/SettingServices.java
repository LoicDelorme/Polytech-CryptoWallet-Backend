package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.SettingForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class SettingServices extends AbstractServices {

    @Autowired
    private DaoRepository<Setting> settingDaoRepository;

    @Autowired
    private DaoRepository<User> userDaoRepository;

    public List<Setting> all() throws UnknownEntityException {
        final List<Setting> settings = this.settingDaoRepository.getAll();
        if (settings == null) {
            throw new UnknownEntityException();
        }

        return settings;
    }

    public Setting get(int id) throws UnknownEntityException {
        final Setting setting = this.settingDaoRepository.get(id);
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
        setting.setUser(this.userDaoRepository.get(settingForm.getUserId()));

        validate(setting);
        this.settingDaoRepository.insert(setting);

        return setting;
    }

    public Setting update(int id, SettingForm settingForm) throws UnknownEntityException, InvalidEntityException {
        final Setting setting = this.settingDaoRepository.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        setting.setName(settingForm.getName());
        setting.setTheme(settingForm.getTheme());
        setting.setLastUpdate(LocalDateTime.now());

        validate(setting);
        this.settingDaoRepository.update(setting);

        return setting;
    }

    public void delete(int id) throws UnknownEntityException {
        final Setting setting = this.settingDaoRepository.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        this.settingDaoRepository.delete(setting);
    }
}