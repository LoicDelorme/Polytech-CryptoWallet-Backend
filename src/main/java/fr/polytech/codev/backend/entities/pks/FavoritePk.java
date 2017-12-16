package fr.polytech.codev.backend.entities.pks;

import fr.polytech.codev.backend.entities.Cryptocurrency;
import fr.polytech.codev.backend.entities.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class FavoritePk implements Serializable {

    private Cryptocurrency cryptocurrency;

    private User user;
}