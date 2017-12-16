package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.AlertType;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.AlertTypeForm;
import fr.polytech.codev.backend.services.AbstractServices;
import fr.polytech.codev.backend.repositories.sql.impl.AlertTypeSqlDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class AlertTypeServices extends AbstractServices {

    @Autowired
    private AlertTypeSqlDaoRepository alertTypeSqlDaoRepository;

    public List<AlertType> all() throws UnknownEntityException {
        final List<AlertType> alertTypes = this.alertTypeSqlDaoRepository.getAll();
        if (alertTypes == null) {
            throw new UnknownEntityException();
        }

        return alertTypes;
    }

    public AlertType get(int id) throws UnknownEntityException {
        final AlertType alertType = this.alertTypeSqlDaoRepository.get(id);
        if (alertType == null) {
            throw new UnknownEntityException();
        }

        return alertType;
    }

    public AlertType insert(AlertTypeForm alertTypeForm) throws InvalidEntityException {
        final AlertType alertType = new AlertType();
        alertType.setName(alertTypeForm.getName());
        alertType.setCreationDate(LocalDateTime.now());
        alertType.setLastUpdate(LocalDateTime.now());

        validate(alertType);
        this.alertTypeSqlDaoRepository.insert(alertType);

        return alertType;
    }

    public AlertType update(int id, AlertTypeForm alertTypeForm) throws UnknownEntityException, InvalidEntityException {
        final AlertType alertType = this.alertTypeSqlDaoRepository.get(id);
        if (alertType == null) {
            throw new UnknownEntityException();
        }

        alertType.setName(alertTypeForm.getName());
        alertType.setLastUpdate(LocalDateTime.now());

        validate(alertType);
        this.alertTypeSqlDaoRepository.update(alertType);

        return alertType;
    }

    public void delete(int id) throws UnknownEntityException {
        final AlertType alertType = this.alertTypeSqlDaoRepository.get(id);
        if (alertType == null) {
            throw new UnknownEntityException();
        }

        this.alertTypeSqlDaoRepository.delete(alertType);
    }
}