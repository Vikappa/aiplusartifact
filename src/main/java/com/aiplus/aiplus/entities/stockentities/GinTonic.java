package com.aiplus.aiplus.entities.stockentities;

import com.aiplus.aiplus.entities.movimenti.Ordine;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gin_tonics")
public class GinTonic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordine_id")
    private Ordine ordine;

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "ginTonic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExtraQuantity> extras;

    @JsonManagedReference
    @OneToMany(mappedBy = "ginTonic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GarnishQuantity> garnishes;
}