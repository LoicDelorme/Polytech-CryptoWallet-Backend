package fr.polytech.codev.backend.forms;

import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDateTime;

@Data
public class TokenForm {

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    private LocalDateTime endDate;

    private int userId;
}