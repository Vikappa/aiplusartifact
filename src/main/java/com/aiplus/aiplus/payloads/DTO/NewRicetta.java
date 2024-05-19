package com.aiplus.aiplus.payloads.DTO;

import java.util.List;

public record NewRicetta(
        String name,
        Long gin_flavour_id,
        Long flavour_tonica_id,
        List<ExtraQuantityDTO> extras,
        List<GarnishQuantityDTO> garnishes
) {}
