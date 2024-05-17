package com.aiplus.aiplus.payloads.records;

import com.aiplus.aiplus.entities.stockentities.Flavour;

public record ExtraRowLineShort(
        String name,
        String um,
        Flavour flavour
) {}
