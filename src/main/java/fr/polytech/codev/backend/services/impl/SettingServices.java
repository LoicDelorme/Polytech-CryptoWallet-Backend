package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.ChartPeriod;
import fr.polytech.codev.backend.entities.Currency;
import fr.polytech.codev.backend.entities.Setting;
import fr.polytech.codev.backend.entities.Theme;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.SettingForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class SettingServices extends AbstractServices {

    @Autowired
    private DaoRepository<Setting> settingDaoRepository;

    @Autowired
    private DaoRepository<Theme> themeDaoRepository;

    @Autowired
    private DaoRepository<Currency> currencyDaoRepository;

    @Autowired
    private DaoRepository<ChartPeriod> chartPeriodDaoRepository;

    public List<Setting> all() throws UnknownEntityException {
        final List<Setting> settings = this.settingDaoRepository.getAll();
        if (settings == null) {
            throw new UnknownEntityException();
        }

        return settings;
    }

    public Setting get(int id) throws UnknownEntityException {
        final Setting setting = this.settingDaoRepository.get(id);
        if (setting == null) {
            throw new UnknownEntityException();
        }

        return setting;
    }

    public Setting insert(SettingForm settingForm) throws InvalidEntityException {
        final Setting setting = new Setting();
        setting.setCreationDate(LocalDateTime.now());
        setting.setLastUpdate(LocalDateTime.now());
        setting.setTheme(this.themeDaoRepository.get(settingForm.getThemeId()));
        setting.setCurrency(this.currencyDaoRepository.get(settingForm.getCurrencyId()));
        setting.setChartPeriod(this.chartPeriodDaoRepository.get(settingForm.getChartPeriodId()));

        validate(setting);
        this.settingDaoRepository.insert(setting);

        return setting;
    }

    public Setting update(int id, SettingForm settingForm) throws UnknownEntityException, InvalidEntityException {
        final Setting setting = get(id);
        setting.setLastUpdate(LocalDateTime.now());
        setting.setTheme(this.themeDaoRepository.get(settingForm.getThemeId()));
        setting.setCurrency(this.currencyDaoRepository.get(settingForm.getCurrencyId()));
        setting.setChartPeriod(this.chartPeriodDaoRepository.get(settingForm.getChartPeriodId()));

        validate(setting);
        this.settingDaoRepository.update(setting);

        return setting;
    }

    public void delete(int id) throws UnknownEntityException {
        this.settingDaoRepository.delete(get(id));
    }
}