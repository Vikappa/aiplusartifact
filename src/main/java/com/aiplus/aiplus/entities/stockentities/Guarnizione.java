package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("GUARNIZIONE")
public class Guarnizione extends Prodotto {
    FLAVOURS flavour;
    COLORS_GUARNIZIONI colore;
}
