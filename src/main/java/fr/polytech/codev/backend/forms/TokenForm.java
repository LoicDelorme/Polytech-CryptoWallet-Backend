package fr.polytech.codev.backend.forms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenForm {

    private int id;

    private String value;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    private int userId;
}