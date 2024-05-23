package com.aiplus.aiplus.payloads.DTO;

import com.aiplus.aiplus.entities.stockentities.*;

import java.util.ArrayList;

public record GinTonicDTO(
        String nome,
        GinBottle ginBottle,
        Tonica tonica,

        ArrayList<ExtraQuantity> extras,
        ArrayList<GarnishQuantity> garnishes
) {
}
