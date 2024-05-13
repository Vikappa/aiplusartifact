package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("GUARNIZIONE")
public class Guarnizione extends Prodotto {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flavour_id", nullable = false)
    private Flavour flavour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colore_id")
    private ColoreGuarnizione colore;

}
