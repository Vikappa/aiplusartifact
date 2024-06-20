package com.aiplus.aiplus.payloads.DTO.totalresumeDTOs;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

public record AllextraresumeDTO(String name,
String UM,
Integer qtaExtra,
Long availableQuantity,
Long nCarico,
String flavour,
LocalDate scadenzaIngrediente
) {
}
