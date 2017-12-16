package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.LogForm;
import fr.polytech.codev.backend.services.AbstractServices;
import fr.polytech.codev.backend.repositories.sql.impl.LogSqlDaoRepository;
import fr.polytech.codev.backend.repositories.sql.impl.UserSqlDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class LogServices extends AbstractServices {

    @Autowired
    private LogSqlDaoRepository logSqlDaoRepository;

    @Autowired
    private UserSqlDaoRepository userSqlDaoRepository;

    public List<Log> all() throws UnknownEntityException {
        final List<Log> logs = this.logSqlDaoRepository.getAll();
        if (logs == null) {
            throw new UnknownEntityException();
        }

        return logs;
    }

    public Log get(int id) throws UnknownEntityException {
        final Log log = this.logSqlDaoRepository.get(id);
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
        log.setUser(this.userSqlDaoRepository.get(logForm.getUserId()));

        validate(log);
        this.logSqlDaoRepository.insert(log);

        return log;
    }

    public Log update(int id, LogForm logForm) throws UnknownEntityException, InvalidEntityException {
        final Log log = this.logSqlDaoRepository.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        log.setIpAddress(logForm.getIpAddress());
        log.setLastUpdate(LocalDateTime.now());

        validate(log);
        this.logSqlDaoRepository.update(log);

        return log;
    }

    public void delete(int id) throws UnknownEntityException {
        final Log log = this.logSqlDaoRepository.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        this.logSqlDaoRepository.delete(log);
    }
}