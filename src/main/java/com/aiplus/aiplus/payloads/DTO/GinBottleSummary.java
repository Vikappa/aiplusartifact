package com.aiplus.aiplus.payloads.DTO;

public record GinBottleSummary(
        String name,
        String ginFlavourName,
        double ginBrandSurcharge,
        String ginBrandName
) {
}
