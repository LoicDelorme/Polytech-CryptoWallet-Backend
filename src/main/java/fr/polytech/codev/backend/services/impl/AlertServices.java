package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Alert;
import fr.polytech.codev.backend.entities.AlertType;
import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AlertForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class AlertServices extends AbstractServices {

    @Autowired
    private DaoRepository<Alert> alertDaoRepository;

    @Autowired
    private DaoRepository<User> userDaoRepository;

    @Autowired
    private DaoRepository<Cryptocurrency> cryptocurrencyDaoRepository;

    @Autowired
    private DaoRepository<AlertType> alertTypeDaoRepository;

    public List<Alert> all() throws UnknownEntityException {
        final List<Alert> alerts = this.alertDaoRepository.getAll();
        if (alerts == null) {
            throw new UnknownEntityException();
        }

        return alerts;
    }

    public Alert get(int id) throws UnknownEntityException {
        final Alert alert = this.alertDaoRepository.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        return alert;
    }

    public Alert insert(AlertForm alertForm) throws InvalidEntityException {
        final Alert alert = new Alert();
        alert.setName(alertForm.getName());
        alert.setThreshold(alertForm.getThreshold());
        alert.setOneShot(alertForm.isOneShot());
        alert.setActive(alertForm.isActive());
        alert.setCreationDate(LocalDateTime.now());
        alert.setLastUpdate(LocalDateTime.now());
        alert.setUser(this.userDaoRepository.get(alertForm.getUserId()));
        alert.setCryptocurrency(this.cryptocurrencyDaoRepository.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeDaoRepository.get(alertForm.getTypeId()));

        validate(alert);
        this.alertDaoRepository.insert(alert);

        return alert;
    }

    public Alert update(int id, AlertForm alertForm) throws UnknownEntityException, InvalidEntityException {
        final Alert alert = this.alertDaoRepository.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        alert.setName(alertForm.getName());
        alert.setThreshold(alertForm.getThreshold());
        alert.setOneShot(alertForm.isOneShot());
        alert.setActive(alertForm.isActive());
        alert.setLastUpdate(LocalDateTime.now());
        alert.setCryptocurrency(this.cryptocurrencyDaoRepository.get(alertForm.getCryptocurrencyId()));
        alert.setType(this.alertTypeDaoRepository.get(alertForm.getTypeId()));

        validate(alert);
        this.alertDaoRepository.update(alert);

        return alert;
    }

    public void delete(int id) throws UnknownEntityException {
        final Alert alert = this.alertDaoRepository.get(id);
        if (alert == null) {
            throw new UnknownEntityException();
        }

        this.alertDaoRepository.delete(alert);
    }
}