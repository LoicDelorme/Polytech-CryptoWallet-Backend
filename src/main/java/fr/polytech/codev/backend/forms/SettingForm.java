package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class SettingForm {

    private int id;

    private String name;

    private String theme;

    private int userId;
}