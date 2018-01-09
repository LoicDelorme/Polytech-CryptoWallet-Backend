package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class LogForm {

    private String ipAddress;

    private String platform;

    private int userId;
}