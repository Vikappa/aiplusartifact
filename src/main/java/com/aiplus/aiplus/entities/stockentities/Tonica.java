package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("TONICA")
public class Tonica extends Prodotto {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flavour_id", nullable = false)
    private Flavour flavour;

    @Column(name = "scadenza_tonica")
    private LocalDate scadenza_tonica;
}
