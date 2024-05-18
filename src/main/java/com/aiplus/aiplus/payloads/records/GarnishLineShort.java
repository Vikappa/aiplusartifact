package com.aiplus.aiplus.payloads.records;

import com.aiplus.aiplus.entities.stockentities.ColoreGuarnizione;
import com.aiplus.aiplus.entities.stockentities.Flavour;

public record GarnishLineShort(
        String name,
        String um,
        ColoreGuarnizione color,
        Flavour flavour
) {}
