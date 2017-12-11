package fr.polytech.codev.backend.controllers.services;

import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.LogForm;
import fr.polytech.codev.backend.services.LogSqlDaoServices;
import fr.polytech.codev.backend.services.UserSqlDaoServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class LogControllerServices extends AbstractControllerServices {

    @Autowired
    private LogSqlDaoServices logSqlDaoServices;

    @Autowired
    private UserSqlDaoServices userSqlDaoServices;

    public List<Log> all() throws UnknownEntityException {
        final List<Log> logs = this.logSqlDaoServices.getAll();
        if (logs == null) {
            throw new UnknownEntityException();
        }

        return logs;
    }

    public Log get(int id) throws UnknownEntityException {
        final Log log = this.logSqlDaoServices.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        return log;
    }

    public Log insert(LogForm logForm) throws InvalidEntityException {
        final Log log = new Log();
        log.setIpAddress(logForm.getIpAddress());
        log.setCreationDate(LocalDateTime.now());
        log.setLastUpdate(LocalDateTime.now());
        log.setUser(this.userSqlDaoServices.get(logForm.getUserId()));

        validate(log);
        this.logSqlDaoServices.insert(log);

        return log;
    }

    public Log update(int id, LogForm logForm) throws UnknownEntityException, InvalidEntityException {
        final Log log = this.logSqlDaoServices.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        log.setIpAddress(logForm.getIpAddress());
        log.setLastUpdate(LocalDateTime.now());

        validate(log);
        this.logSqlDaoServices.update(log);

        return log;
    }

    public void delete(int id) throws UnknownEntityException {
        final Log log = this.logSqlDaoServices.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        this.logSqlDaoServices.delete(log);
    }
}