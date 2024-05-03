package com.aiplus.aiplus.payloads.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GinBrandDTO {
    private String name;
    private String imageUrl;
    private MultipartFile imageFile;
    private String description;
    private double sovrapprezzo;

}
