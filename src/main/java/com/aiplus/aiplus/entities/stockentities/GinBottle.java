package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gin_bottle")
public class GinBottle extends Prodotto {


    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private GinBrand brand;

    @Column(name = "production_date", nullable = false)
    private LocalDate productionDate;

    @Column(nullable = false, name = "volume")
    private double volume;

    @Column(name = "current_volume")
    private double currentVolume;

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

