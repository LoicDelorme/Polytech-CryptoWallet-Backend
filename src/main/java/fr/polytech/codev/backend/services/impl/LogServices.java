package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Log;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.LogForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class LogServices extends AbstractServices {

    @Autowired
    private DaoRepository<Log> logDaoRepository;

    @Autowired
    private DaoRepository<User> userDaoRepository;

    public List<Log> all() throws UnknownEntityException {
        final List<Log> logs = this.logDaoRepository.getAll();
        if (logs == null) {
            throw new UnknownEntityException();
        }

        return logs;
    }

    public Log get(int id) throws UnknownEntityException {
        final Log log = this.logDaoRepository.get(id);
        if (log == null) {
            throw new UnknownEntityException();
        }

        return log;
    }

    public Log insert(LogForm logForm) throws InvalidEntityException {
        final Log log = new Log();
        log.setIpAddress(logForm.getIpAddress());
        log.setPlatform(logForm.getPlatform());
        log.setCreationDate(LocalDateTime.now());
        log.setLastUpdate(LocalDateTime.now());
        log.setUser(this.userDaoRepository.get(logForm.getUserId()));

        validate(log);
        this.logDaoRepository.insert(log);

        return log;
    }

    public Log update(int id, LogForm logForm) throws UnknownEntityException, InvalidEntityException {
        final Log log = get(id);
        log.setIpAddress(logForm.getIpAddress());
        log.setPlatform(logForm.getPlatform());
        log.setLastUpdate(LocalDateTime.now());

        validate(log);
        this.logDaoRepository.update(log);

        return log;
    }

    public void delete(int id) throws UnknownEntityException {
        this.logDaoRepository.delete(get(id));
    }
}