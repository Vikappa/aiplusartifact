package com.aiplus.aiplus.entities.movimenti;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IngredienteRicetta {
    @Id
    @Column(updatable = false, nullable = false)
    private String name;

    private int quantita_ingrediente;

    private String um;
}
