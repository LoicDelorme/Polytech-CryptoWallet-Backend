package fr.polytech.codev.backend.entities;

import fr.polytech.codev.backend.adapters.AlertTypeAdapter;
import fr.polytech.codev.backend.adapters.CryptocurrencyAdapter;
import fr.polytech.codev.backend.adapters.UserAdapter;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "alerts")
public class Alert implements fr.polytech.codev.backend.entities.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "The threshold can't be null!")
    @PositiveOrZero(message = "The threshold can't be negative!")
    @Column(name = "threshold")
    private BigDecimal threshold;

    @Column(name = "is_one_shot")
    private boolean isOneShot;

    @Column(name = "is_active")
    private boolean isActive;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    @NotNull(message = "The creation date can't be null!")
    @PastOrPresent(message = "The creation date can't be in the future!")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    @NotNull(message = "The last update can't be null!")
    @PastOrPresent(message = "The last update can't be in the future!")
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @JsonbProperty("userId")
    @JsonbTypeAdapter(UserAdapter.class)
    @NotNull(message = "The user can't be null!")
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @JsonbProperty("cryptocurrencyId")
    @JsonbTypeAdapter(CryptocurrencyAdapter.class)
    @NotNull(message = "The cryptocurrency can't be null!")
    @ManyToOne
    @JoinColumn(name = "cryptocurrency")
    private Cryptocurrency cryptocurrency;

    @JsonbProperty("typeId")
    @JsonbTypeAdapter(AlertTypeAdapter.class)
    @NotNull(message = "The type can't be null!")
    @ManyToOne
    @JoinColumn(name = "type")
    private AlertType type;
}