package fr.polytech.codev.backend.forms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlertForm {

    private String name;

    private BigDecimal threshold;

    private boolean isOneShot;

    private boolean isActive;

    private int userId;

    private int cryptocurrencyId;

    private int typeId;
}