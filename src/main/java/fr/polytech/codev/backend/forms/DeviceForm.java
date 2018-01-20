package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class DeviceForm {

    private String platform;

    private String uuid;

    private int userId;
}