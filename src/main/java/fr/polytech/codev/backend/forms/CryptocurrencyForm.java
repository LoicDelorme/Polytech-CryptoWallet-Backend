package fr.polytech.codev.backend.forms;

import lombok.Data;

@Data
public class CryptocurrencyForm {

    private String name;

    private String symbol;

    private String imageUrl;

    private String baseUrl;

    private String resourceUrl;
}