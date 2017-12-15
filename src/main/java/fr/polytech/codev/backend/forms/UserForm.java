package fr.polytech.codev.backend.forms;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

@Data
public class UserForm {

    private String lastname;

    private String firstname;

    private String email;

    private String password;

    @JsonbProperty("isEnabled")
    private boolean isEnabled;

    @JsonbProperty("isAdministrator")
    private boolean isAdministrator;
}