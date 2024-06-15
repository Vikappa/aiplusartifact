package com.aiplus.aiplus.payloads.DTO.totalresumeDTOs;

import java.time.LocalDate;

public record AllbottlesresumeDTO(
        Long id,
        String um,
        String name,
        Double alcoholPercentage,
        Double currentVolume,
        LocalDate expirationDate,
        LocalDate productionDate,
        Double volume,
        Long nCarico,
        String caricoData,
        String brandName,
        String flavourName
) {
}
