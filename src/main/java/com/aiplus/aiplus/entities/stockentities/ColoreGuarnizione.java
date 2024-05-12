package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.*;

@Entity
@Table(name = "colori_guarnizione")
public class ColoreGuarnizione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}