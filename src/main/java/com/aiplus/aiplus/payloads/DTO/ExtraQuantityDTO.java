package com.aiplus.aiplus.payloads.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtraQuantityDTO {
    private String extraId;
    private String extraName;
    private int quantity;
    private String UM;

    public ExtraQuantityDTO(String extraId, String extraName, int quantity, String UM) {
        this.extraId = extraId;
        this.extraName = extraName;
        this.quantity = quantity;
        this.UM = UM;
    }

}