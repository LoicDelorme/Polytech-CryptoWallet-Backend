package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class SettingForm {

    private String name;

    private String theme;

    private int userId;
}