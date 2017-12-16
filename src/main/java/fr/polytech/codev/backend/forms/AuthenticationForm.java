package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class AuthenticationForm {

    private String email;

    private String password;
}