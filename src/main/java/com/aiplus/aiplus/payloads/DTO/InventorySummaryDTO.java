package com.aiplus.aiplus.payloads.DTO;

import com.aiplus.aiplus.entities.stockentities.Flavour;

import java.time.LocalDate;

public interface InventorySummaryDTO {
    String getName();
    String getUm();
    Flavour getFlavour();
    String getBrand();
    String getColor();
    LocalDate getExpirationDate();
    Double getTotalVolume();
    Integer getTotalQuantity();
}
