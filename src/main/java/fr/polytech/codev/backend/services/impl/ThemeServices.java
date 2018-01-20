package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Theme;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.ThemeForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class ThemeServices extends AbstractServices {

    @Autowired
    private DaoRepository<Theme> themeDaoRepository;

    public List<Theme> all() throws UnknownEntityException {
        final List<Theme> themes = this.themeDaoRepository.getAll();
        if (themes == null) {
            throw new UnknownEntityException();
        }

        return themes;
    }

    public Theme get(int id) throws UnknownEntityException {
        final Theme theme = this.themeDaoRepository.get(id);
        if (theme == null) {
            throw new UnknownEntityException();
        }

        return theme;
    }

    public Theme insert(ThemeForm themeForm) throws InvalidEntityException {
        final Theme theme = new Theme();
        theme.setName(themeForm.getName());
        theme.setCreationDate(LocalDateTime.now());
        theme.setLastUpdate(LocalDateTime.now());

        validate(theme);
        this.themeDaoRepository.insert(theme);

        return theme;
    }

    public Theme update(int id, ThemeForm themeForm) throws UnknownEntityException, InvalidEntityException {
        final Theme theme = get(id);
        theme.setName(themeForm.getName());
        theme.setLastUpdate(LocalDateTime.now());

        validate(theme);
        this.themeDaoRepository.update(theme);

        return theme;
    }

    public void delete(int id) throws UnknownEntityException {
        this.themeDaoRepository.delete(get(id));
    }
}