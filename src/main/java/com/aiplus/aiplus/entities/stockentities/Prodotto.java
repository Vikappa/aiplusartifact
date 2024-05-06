package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public String UM;

    public String name;

}
