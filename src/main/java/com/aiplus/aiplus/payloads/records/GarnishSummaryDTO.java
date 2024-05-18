package com.aiplus.aiplus.payloads.records;

public interface GarnishSummaryDTO {
    String getName();
    String getUm();
    String getFlavour();
    String getColor();
    Long getTotalQuantity();
    default String getDiscriminatorType() {
        return "GUARNIZIONE";
    }
}