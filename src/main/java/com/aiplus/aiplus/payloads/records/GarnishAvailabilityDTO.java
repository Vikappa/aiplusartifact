package com.aiplus.aiplus.payloads.records;
import com.aiplus.aiplus.entities.stockentities.Guarnizione;

public class GarnishAvailabilityDTO {
    private Guarnizione guarnizione;
    private int availableQuantity;

    public GarnishAvailabilityDTO(Guarnizione guarnizione, int availableQuantity) {
        this.guarnizione = guarnizione;
        this.availableQuantity = availableQuantity;
    }

    public Guarnizione getGuarnizione() {
        return guarnizione;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}

