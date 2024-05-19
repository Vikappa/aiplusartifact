package com.aiplus.aiplus.entities.stockentities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ricette")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ricetta {

    private final int BASE_PRICE = 7; // €

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gin_flavour_id")
    private GinFlavour ginFlavour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flavour_tonica_id")
    private Flavour tonica;

    @JsonManagedReference
    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExtraQuantity> extras;

    @JsonManagedReference
    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GarnishQuantity> garnishes;
}
