package com.aiplus.aiplus.payloads.records;
import com.aiplus.aiplus.entities.stockentities.Extra;
import com.aiplus.aiplus.entities.stockentities.Extra;

public class ExtraAvailabilityDTO {
    private Extra extra;
    private int availableQuantity;

    public ExtraAvailabilityDTO(Extra extra, int availableQuantity) {
        this.extra = extra;
        this.availableQuantity = availableQuantity;
    }

    public Extra getExtra() {
        return extra;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}

