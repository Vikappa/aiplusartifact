package com.aiplus.aiplus.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gin_bottle")
public class GinBottle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private GinBrand brand;

    @Column(name = "production_date", nullable = false)
    private LocalDate productionDate;

    @Column(nullable = false)
    private double volume;

    @Column(name = "alcohol_percentage", nullable = false)
    private double alcoholPercentage;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "batch_number", nullable = false)
    private long batchNumber;

    @Column(name = "image_url")
    private String imageUrl;

    private GINFLAVOUR flavour;

    public void setFlavour(GINFLAVOUR flavour) {
        this.flavour = GINFLAVOUR.valueOf(flavour.name());
    }
}

