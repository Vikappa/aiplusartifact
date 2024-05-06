package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GinBrand {

    @Id
    @Column(updatable = false, nullable = false)
    private String name;

    private String description;

    private String imageUrl;

    private double sovrapprezzo;


}
