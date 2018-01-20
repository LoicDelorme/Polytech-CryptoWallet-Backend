package fr.polytech.codev.backend.forms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenForm {

    private LocalDateTime endDate;

    private String platform;

    private int userId;
}