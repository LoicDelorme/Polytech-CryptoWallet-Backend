package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.ChartPeriod;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.ChartPeriodForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class ChartPeriodServices extends AbstractServices {

    @Autowired
    private DaoRepository<ChartPeriod> chartPeriodDaoRepository;

    public List<ChartPeriod> all() throws UnknownEntityException {
        final List<ChartPeriod> chartPeriods = this.chartPeriodDaoRepository.getAll();
        if (chartPeriods == null) {
            throw new UnknownEntityException();
        }

        return chartPeriods;
    }

    public ChartPeriod get(int id) throws UnknownEntityException {
        final ChartPeriod chartPeriod = this.chartPeriodDaoRepository.get(id);
        if (chartPeriod == null) {
            throw new UnknownEntityException();
        }

        return chartPeriod;
    }

    public ChartPeriod insert(ChartPeriodForm chartPeriodForm) throws InvalidEntityException {
        final ChartPeriod chartPeriod = new ChartPeriod();
        chartPeriod.setName(chartPeriodForm.getName());
        chartPeriod.setCreationDate(LocalDateTime.now());
        chartPeriod.setLastUpdate(LocalDateTime.now());

        validate(chartPeriod);
        this.chartPeriodDaoRepository.insert(chartPeriod);

        return chartPeriod;
    }

    public ChartPeriod update(int id, ChartPeriodForm chartPeriodForm) throws UnknownEntityException, InvalidEntityException {
        final ChartPeriod chartPeriod = get(id);
        chartPeriod.setName(chartPeriodForm.getName());
        chartPeriod.setLastUpdate(LocalDateTime.now());

        validate(chartPeriod);
        this.chartPeriodDaoRepository.update(chartPeriod);

        return chartPeriod;
    }

    public void delete(int id) throws UnknownEntityException {
        this.chartPeriodDaoRepository.delete(get(id));
    }
}