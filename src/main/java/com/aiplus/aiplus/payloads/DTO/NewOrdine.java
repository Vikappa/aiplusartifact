package com.aiplus.aiplus.payloads.DTO;

import java.util.ArrayList;

public record NewOrdine(
        int numTav,
        ArrayList<GinTonicDTO> gintonics
) {
}
