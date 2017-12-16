package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AlertForm;
import fr.polytech.codev.backend.services.AbstractServices;
import fr.polytech.codev.backend.repositories.sql.impl.AlertSqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.AlertTypeSqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.CryptocurrencySqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.UserSqlDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class AlertServices extends AbstractServices {

    @Autowired
    private AlertSqlDaoRepository alertSqlDaoRepository;

    @Autowired
    private UserSqlDaoRepository userSqlDaoRepository;

    @Autowired
    private CryptocurrencySqlDaoRepository cryptocurrencySqlDaoRepository;

    @Autowired
    private AlertTypeSqlDaoRepository alertTypeSqlDaoRepository;

    public List<Alert> all() throws UnknownEntityException {
        final List<Alert> alerts = this.alertSqlDaoRepository.getAll();
        if (alerts == null) {
            throw new UnknownEntityException();
        }

        return alerts;
    }

    public Alert get(int id) throws UnknownEntityException {
        final Alert alert = this.alertSqlDaoRepository.get(id);
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
        alert.setUser(this.userSqlDaoRepository.get(alertForm.getUserId()));
        alert.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeSqlDaoRepository.get(alertForm.getTypeId()));

        validate(alert);
        this.alertSqlDaoRepository.insert(alert);

        return alert;
    }

    public Alert update(int id, AlertForm alertForm) throws UnknownEntityException, InvalidEntityException {
        final Alert alert = this.alertSqlDaoRepository.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        alert.setThreshold(alertForm.getThreshold());
        alert.setOneShot(alertForm.isOneShot());
        alert.setActive(alertForm.isActive());
        alert.setLastUpdate(LocalDateTime.now());
        alert.setCryptocurrency(this.cryptocurrencySqlDaoRepository.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeSqlDaoRepository.get(alertForm.getTypeId()));

        validate(alert);
        this.alertSqlDaoRepository.update(alert);

        return alert;
    }

    public void delete(int id) throws UnknownEntityException {
        final Alert alert = this.alertSqlDaoRepository.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        this.alertSqlDaoRepository.delete(alert);
    }
}