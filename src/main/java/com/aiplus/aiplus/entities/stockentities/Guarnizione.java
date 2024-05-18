package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("GUARNIZIONE")
public class Guarnizione extends Prodotto {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flavour_id")
    private Flavour flavour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colore_id")
    private ColoreGuarnizione colore;

    private Integer quantitaGarnish;
}
