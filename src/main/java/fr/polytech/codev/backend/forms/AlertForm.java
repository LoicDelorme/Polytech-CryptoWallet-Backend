package fr.polytech.codev.backend.forms;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.math.BigDecimal;

@Data
public class AlertForm {

    private BigDecimal threshold;

    @JsonbProperty("isOneShot")
    private boolean isOneShot;

    @JsonbProperty("isActive")
    private boolean isActive;

    private int userId;

    private int cryptocurrencyId;

    private int typeId;
}