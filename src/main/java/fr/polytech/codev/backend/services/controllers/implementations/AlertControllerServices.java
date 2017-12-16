package fr.polytech.codev.backend.services.controllers.implementations;

import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AlertForm;
import fr.polytech.codev.backend.services.controllers.AbstractControllerServices;
import fr.polytech.codev.backend.services.dao.implementations.AlertSqlDaoServices;
import fr.polytech.codev.backend.services.dao.implementations.AlertTypeSqlDaoServices;
import fr.polytech.codev.backend.services.dao.implementations.CryptocurrencySqlDaoServices;
import fr.polytech.codev.backend.services.dao.implementations.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class AlertControllerServices extends AbstractControllerServices {

    @Autowired
    private AlertSqlDaoServices alertSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    @Autowired
    private CryptocurrencySqlDaoServices cryptocurrencySqlDaoServices;

    @Autowired
    private AlertTypeSqlDaoServices alertTypeSqlDaoServices;

    public List<Alert> all() throws UnknownEntityException {
        final List<Alert> alerts = this.alertSqlDaoServices.getAll();
        if (alerts == null) {
            throw new UnknownEntityException();
        }

        return alerts;
    }

    public Alert get(int id) throws UnknownEntityException {
        final Alert alert = this.alertSqlDaoServices.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        return alert;
    }

    public Alert insert(AlertForm alertForm) throws InvalidEntityException {
        final Alert alert = new Alert();
        alert.setThreshold(alertForm.getThreshold());
        alert.setOneShot(alertForm.isOneShot());
        alert.setActive(alertForm.isActive());
        alert.setCreationDate(LocalDateTime.now());
        alert.setLastUpdate(LocalDateTime.now());
        alert.setUser(this.userSqlDaoServices.get(alertForm.getUserId()));
        alert.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeSqlDaoServices.get(alertForm.getTypeId()));

        validate(alert);
        this.alertSqlDaoServices.insert(alert);

        return alert;
    }

    public Alert update(int id, AlertForm alertForm) throws UnknownEntityException, InvalidEntityException {
        final Alert alert = this.alertSqlDaoServices.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        alert.setThreshold(alertForm.getThreshold());
        alert.setOneShot(alertForm.isOneShot());
        alert.setActive(alertForm.isActive());
        alert.setLastUpdate(LocalDateTime.now());
        alert.setCryptocurrency(this.cryptocurrencySqlDaoServices.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeSqlDaoServices.get(alertForm.getTypeId()));

        validate(alert);
        this.alertSqlDaoServices.update(alert);

        return alert;
    }

    public void delete(int id) throws UnknownEntityException {
        final Alert alert = this.alertSqlDaoServices.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        this.alertSqlDaoServices.delete(alert);
    }
}