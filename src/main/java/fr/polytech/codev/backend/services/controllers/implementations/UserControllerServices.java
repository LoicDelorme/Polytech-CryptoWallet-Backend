package fr.polytech.codev.backend.services.controllers.implementations;

import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.UserForm;
import fr.polytech.codev.backend.services.controllers.AbstractControllerServices;
import fr.polytech.codev.backend.services.dao.implementations.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class UserControllerServices extends AbstractControllerServices {

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    public List<User> all() throws UnknownEntityException {
        final List<User> users = this.userSqlDaoServices.getAll();
        if (users == null) {
            throw new UnknownEntityException();
        }

        return users;
    }

    public User get(int id) throws UnknownEntityException {
        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        return user;
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

        validate(user);
        this.userSqlDaoServices.insert(user);

        return user;
    }

    public User update(int id, UserForm userForm) throws UnknownEntityException, InvalidEntityException {
        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

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

        return user;
    }

    public void updateLastActivity(int id) throws UnknownEntityException {
        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        user.setLastActivity(LocalDateTime.now());
        this.userSqlDaoServices.update(user);
    }

    public void delete(int id) throws UnknownEntityException {
        final User user = this.userSqlDaoServices.get(id);
        if (user == null) {
            throw new UnknownEntityException();
        }

        this.userSqlDaoServices.delete(user);
    }
}