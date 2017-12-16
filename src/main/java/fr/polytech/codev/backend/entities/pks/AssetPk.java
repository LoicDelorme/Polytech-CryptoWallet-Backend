package fr.polytech.codev.backend.entities.pks;

import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.entities.Wallet;
import lombok.Data;

import java.io.Serializable;

@Data
public class AssetPk implements Serializable {

    private Wallet wallet;

    private Cryptocurrency cryptocurrency;
}