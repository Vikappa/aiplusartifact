package com.aiplus.aiplus.payloads.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public record NewProdotto (
    String UM,
    String name,
    String discriminatorString,

    String brandId,
    LocalDate productionDate,
    Double volume,
    Double currentVolume,
    Double alcoholPercentage,
    LocalDate expirationDate,
    LocalDate scadenza_tonica,
    String batchNumber,
    String imageUrl,
    String ginFlavourId,

    String flavourId,
    LocalDate scadenza_ingrediente,

    String coloreId,
    String brand_tonica_name

){}
