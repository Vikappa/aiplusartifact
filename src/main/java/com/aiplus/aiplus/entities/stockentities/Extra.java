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
@DiscriminatorValue("ALIMENTO_EXTRA")
public class Extra extends Prodotto {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flavour_id")
    private Flavour flavour;

    @Column(name = "scadenza_ingrediente")
    private LocalDate scadenza_ingrediente;

    @Column(name = "quantita_extra")
    private Integer qtaExtra;

    public Integer getQtaExtra() {
        return qtaExtra;
    }
    public void setQtaExtra(int qtaExtra) {
        this.qtaExtra = qtaExtra;
    }
}
