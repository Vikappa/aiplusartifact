package com.aiplus.aiplus.entities.stockentities;

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
public class GinFlavour {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flavour_seq")
    @SequenceGenerator(name = "flavour_seq", sequenceName = "flavour_sequence", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
