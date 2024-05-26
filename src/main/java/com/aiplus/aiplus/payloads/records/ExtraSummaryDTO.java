package com.aiplus.aiplus.payloads.records;

public interface ExtraSummaryDTO {
    String getName();
    String getUm();
    String getFlavour();
    Long getTotalQuantity();

    default String getDiscriminatorType() {
        return "ALIMENTO_EXTRA";
    }
}
