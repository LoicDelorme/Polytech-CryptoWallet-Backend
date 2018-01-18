package fr.polytech.codev.backend.entities;

import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements fr.polytech.codev.backend.entities.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "The lastname can't be blank!")
    @Size(message = "The lastname can't exceed 250 characters!", max = 250)
    @Column(name = "lastname")
    private String lastname;

    @NotBlank(message = "The firstname can't be blank!")
    @Size(message = "The firstname can't exceed 250 characters!", max = 250)
    @Column(name = "firstname")
    private String firstname;

    @NotBlank(message = "The email can't be blank!")
    @Size(message = "The email can't exceed 250 characters!", max = 250)
    @Email(message = "The provided email must be a valid email!")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "The password can't be blank!")
    @Size(message = "The password can't exceed 250 characters!", max = 250)
    @Column(name = "password")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "is_administrator")
    private boolean isAdministrator;

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

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    @NotNull(message = "The last activity can't be null!")
    @PastOrPresent(message = "The last activity can't be in the future!")
    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    @JsonbTransient
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites", joinColumns = { @JoinColumn(name = "user") }, inverseJoinColumns = { @JoinColumn(name = "cryptocurrency") })
    private List<Cryptocurrency> favorites = new ArrayList<Cryptocurrency>();

    @JsonbTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Wallet> wallets = new ArrayList<Wallet>();

    @JsonbTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Alert> alerts = new ArrayList<Alert>();

    @JsonbTransient
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "setting")
    private Setting setting;

    @JsonbTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Token> tokens = new ArrayList<Token>();

    @JsonbTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Log> logs = new ArrayList<Log>();

    @JsonbTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Device> devices = new ArrayList<Device>();
}