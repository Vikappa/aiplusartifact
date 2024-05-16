package com.aiplus.aiplus.entities.stockentities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("GIN_BOTTLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GinBottle extends Prodotto {

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private GinBrand brand;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "volume")
    private double volume;

    @Column(name = "current_volume")
    private double currentVolume;

    @Column(name = "alcohol_percentage")
    private double alcoholPercentage;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "gin_flavour_id")
    private GinFlavour ginFlavour;

}
