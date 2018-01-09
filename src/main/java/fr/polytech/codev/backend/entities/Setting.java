package fr.polytech.codev.backend.entities;

import fr.polytech.codev.backend.adapters.ChartPeriodJsonbAdapter;
import fr.polytech.codev.backend.adapters.CurrencyJsonbAdapter;
import fr.polytech.codev.backend.adapters.ThemeJsonbAdapter;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "settings")
public class Setting implements fr.polytech.codev.backend.entities.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    @JsonbTypeAdapter(ThemeJsonbAdapter.class)
    @NotNull(message = "The theme can't be null!")
    @ManyToOne
    @JoinColumn(name = "theme")
    private Theme theme;

    @JsonbTypeAdapter(CurrencyJsonbAdapter.class)
    @NotNull(message = "The currency can't be null!")
    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    @JsonbTypeAdapter(ChartPeriodJsonbAdapter.class)
    @NotNull(message = "The chart period can't be null!")
    @ManyToOne
    @JoinColumn(name = "chart_period")
    private ChartPeriod chartPeriod;
}