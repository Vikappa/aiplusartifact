package com.aiplus.aiplus.payloads.records;

import java.time.LocalDate;

public interface GinBottleSummaryDTO {
    String getName();
    String getUm();
    String getFlavour();
    LocalDate getExpirationDate();
    Double getTotalVolume();
    Long getTotalQuantity();
    String getGinBrand();
    String getGinImage();
    default String getDiscriminatorType() {
        return "GIN_BOTTLE";
    }
}