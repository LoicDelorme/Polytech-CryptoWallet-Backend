package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AuthenticationForm;
import fr.polytech.codev.backend.forms.UserForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServices extends AbstractServices {

    @Autowired
    private DaoRepository<User> userDaoRepository;

    @Autowired
    private DaoRepository<Setting> settingDaoRepository;

    public List<User> all() throws UnknownEntityException {
        final List<User> users = this.userDaoRepository.getAll();
        if (users == null) {
            throw new UnknownEntityException();
        }

        return users;
    }

    public User get(int id) throws UnknownEntityException {
        final User user = this.userDaoRepository.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return user;
    }

    public User getByCredentials(AuthenticationForm authenticationForm) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("email", authenticationForm.getEmail());
        parameters.put("password", authenticationForm.getPassword());

        final List<User> users = this.userDaoRepository.filter(parameters);
        if (users == null || users.size() != 1) {
            throw new UnknownEntityException();
        }

        return users.get(0);
    }

    public User insert(UserForm userForm) throws InvalidEntityException {
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
        user.setSetting(this.settingDaoRepository.get(userForm.getSettingId()));

        validate(user);
        this.userDaoRepository.insert(user);

        return user;
    }

    public User update(int id, UserForm userForm) throws UnknownEntityException, InvalidEntityException {
        final User user = get(id);
        user.setLastname(userForm.getLastname());
        user.setFirstname(userForm.getFirstname());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setEnabled(userForm.isEnabled());
        user.setAdministrator(userForm.isAdministrator());
        user.setLastUpdate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());

        validate(user);
        this.userDaoRepository.update(user);

        return user;
    }

    public void updateLastActivity(int id) throws UnknownEntityException {
        final User user = get(id);
        user.setLastActivity(LocalDateTime.now());

        this.userDaoRepository.update(user);
    }

    public void delete(int id) throws UnknownEntityException {
        this.userDaoRepository.delete(get(id));
    }
}