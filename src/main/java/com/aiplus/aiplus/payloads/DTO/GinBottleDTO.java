package com.aiplus.aiplus.payloads.DTO;

import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GinBottleDTO {
    private String name;
    private String UM;
    private String brandId;
    private LocalDate productionDate;
    private String imageUrl;
    private String ginFlavourString;
    private double volume;
    private double alcoholPercentage;
    private long batchNumber;


}
