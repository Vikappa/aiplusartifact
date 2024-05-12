package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.*;

@Entity
@Table(name = "FLAVOURS")
public class Flavour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
