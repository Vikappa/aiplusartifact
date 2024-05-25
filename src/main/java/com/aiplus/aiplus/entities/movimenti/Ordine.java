package com.aiplus.aiplus.entities.movimenti;

import com.aiplus.aiplus.entities.stockentities.GinTonic;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordini")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private GinTonic ginTonic;

    private int nTavolo;

    @Enumerated(EnumType.STRING)
    private ORDER_STATUS status;
}
