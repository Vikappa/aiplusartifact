package com.aiplus.aiplus.payloads.DTO;

import java.util.List;

public record NewRicetta(
        String name,
        String gin_flavour_id,
        String flavour_tonica_id,
        List<ExtraQuantityDTO> extras,
        List<GarnishQuantityDTO> garnishes
) {}
