package com.aiplus.aiplus.entities;

import jakarta.persistence.*;
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
public class GinBottle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private GinBrand brand;

    @Column(name = "production_date")
    private LocalDate productionDate;


    @Column(name = "image_url")
    private String imageUrl;

}
