package com.aiplus.aiplus.entities.stockentities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @JsonBackReference
    @OneToMany(mappedBy = "guarnizione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GarnishQuantity> garnishQuantities;
}
