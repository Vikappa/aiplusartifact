package com.aiplus.aiplus.payloads.DTO.totalresumeDTOs;
import java.time.LocalDate;

public record AlltonicheresumeDTO(
        Long id,
        String UM,
        String name,
        String flavourName,
        LocalDate scadenzaTonica,
        String brandName,
        Long nCarico,
        String caricoData
)  {
}
