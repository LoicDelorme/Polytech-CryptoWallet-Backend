package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class SettingForm {

    private int themeId;

    private int currencyId;

    private int chartPeriodId;
}