package fr.polytech.codev.backend.entities;

import fr.polytech.codev.backend.entities.pks.FavoritePk;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Data
@Entity
@Table(name = "favorites")
@IdClass(FavoritePk.class)
public class Favorite implements fr.polytech.codev.backend.entities.Entity {

    @Id
    @ManyToOne
    @JoinColumn(name = "cryptocurrency")
    private Cryptocurrency cryptocurrency;

    @Id
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}