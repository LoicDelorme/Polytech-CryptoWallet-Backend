package fr.polytech.codev.backend.forms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetForm {

    private int walletId;

    private int cryptocurrencyId;

    private BigDecimal amount;

    private BigDecimal purchasePrice;
}