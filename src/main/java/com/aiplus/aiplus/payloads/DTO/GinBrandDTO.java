package com.aiplus.aiplus.payloads.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GinBrandDTO {
    private String name;
    private String imageUrl;
    private String description;
    private double sovrapprezzo;
}
