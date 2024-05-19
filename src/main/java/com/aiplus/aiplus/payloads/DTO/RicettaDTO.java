package com.aiplus.aiplus.payloads.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RicettaDTO {
    private long id;
    private String name;
    private String ginFlavour;
    private String tonica;
    private List<ExtraQuantityDTO> extras;
    private List<GarnishQuantityDTO> garnishes;

}
