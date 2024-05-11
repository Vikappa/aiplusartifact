package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
public class Tonica extends Prodotto {


    FLAVOURS flavour;

    LocalDate scadenza_tonica;
}
