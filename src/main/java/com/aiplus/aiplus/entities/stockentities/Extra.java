package com.aiplus.aiplus.entities.stockentities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ALIMENTO_EXTRA")
public class Extra extends Prodotto {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flavour_id")
    private Flavour flavour;

    @Column(name = "scadenza_ingrediente")
    private LocalDate scadenza_ingrediente;

    @Getter
    @Column(name = "quantita_extra")
    private Integer qtaExtra;

    public void setQtaExtra(int qtaExtra) {
        this.qtaExtra = qtaExtra;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "extra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExtraQuantity> extraQuantities;
}
