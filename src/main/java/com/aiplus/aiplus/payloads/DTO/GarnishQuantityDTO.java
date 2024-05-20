package com.aiplus.aiplus.payloads.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarnishQuantityDTO {
    private String guarnizioneId;
    private String guarnizioneName;
    private int quantity;
    private String UM;

    public GarnishQuantityDTO(String guarnizioneId, String guarnizioneName, int quantity, String UM) {
        this.guarnizioneId = guarnizioneId;
        this.guarnizioneName = guarnizioneName;
        this.quantity = quantity;
        this.UM = UM;
    }

}