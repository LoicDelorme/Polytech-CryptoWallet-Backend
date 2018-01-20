package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class UserForm {

    private String lastname;

    private String firstname;

    private String email;

    private String password;

    private boolean isEnabled;

    private boolean isAdministrator;

    private int settingId;
}