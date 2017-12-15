package fr.polytech.codev.backend.entities;

import fr.polytech.codev.backend.adapters.CryptocurrencyAdapter;
import fr.polytech.codev.backend.adapters.UserAdapter;
import fr.polytech.codev.backend.entities.pks.FavoritePk;
import lombok.Data;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.*;

@Data
@Entity
@Table(name = "favorites")
@IdClass(FavoritePk.class)
public class Favorite implements fr.polytech.codev.backend.entities.Entity {

    @JsonbTypeAdapter(CryptocurrencyAdapter.class)
    @Id
    @ManyToOne
    @JoinColumn(name = "cryptocurrency")
    private Cryptocurrency cryptocurrency;

    @JsonbTypeAdapter(UserAdapter.class)
    @Id
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}