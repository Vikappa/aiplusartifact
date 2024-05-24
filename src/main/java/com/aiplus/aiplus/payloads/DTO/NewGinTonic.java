package com.aiplus.aiplus.payloads.DTO;

import com.aiplus.aiplus.entities.stockentities.GinBottle;

import java.util.List;

public record NewGinTonic(
        //PER ADESSO NO ORDINE
        String name,

        String ginBottleName,

        String ginBottleBrandName,

        String ginFlavourName,

        String tonicaName,

        String tonicaBrand,

        String tonicaFlavour,


        List<NewExtraQuantity> extras,

        List<NewGarnishQuantity> garnishes,

        Double totalPrice
) {
}
