package com.aiplus.aiplus.payloads.records;

import com.aiplus.aiplus.entities.stockentities.Guarnizione;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GarnishAvailabilityDTO {

    private int availableQuantity;

    private long referenceId;
}
