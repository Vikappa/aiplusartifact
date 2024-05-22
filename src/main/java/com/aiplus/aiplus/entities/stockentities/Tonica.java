package com.aiplus.aiplus.entities.stockentities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tonica extends Prodotto {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flavour_id")
    private Flavour flavour;

    @Column(name = "scadenza_tonica")
    private LocalDate scadenza_tonica;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_tonica_name")
    private BrandTonica brandTonica;

    @OneToOne(mappedBy = "tonica", fetch = FetchType.LAZY)
    @JsonBackReference
    private GinTonic ginTonic;
}
