package com.aiplus.aiplus.payloads.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.aiplus.aiplus.entities.stockentities.GINFLAVOUR;
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
    private GINFLAVOUR flavour;
    private double volume;
    private double alcoholPercentage;
    private long batchNumber;
}
