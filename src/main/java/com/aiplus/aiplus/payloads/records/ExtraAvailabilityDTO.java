package com.aiplus.aiplus.payloads.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtraAvailabilityDTO {

    private int availableQuantity;

    private long idReference;

}
