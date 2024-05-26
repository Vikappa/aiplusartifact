package com.aiplus.aiplus.payloads.records;

public interface TonicaSummaryDTO {
    String getName();
    String getUm();
    String getBrand();
    String getFlavour();
    Long getTotalQuantity();

    default String getDiscriminatorType() {
        return "TONICA";
    }
}
