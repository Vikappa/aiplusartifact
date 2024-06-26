package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.GinBrand;
import com.aiplus.aiplus.payloads.DTO.GinBrandDTO;
import com.aiplus.aiplus.services.GinBrandService;
import com.aiplus.aiplus.utility.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ginbrand")
public class GinBrandController {

    @Autowired
    private GinBrandService ginBrandService;

    @Autowired
    private ImageUploader imageUploader;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addGinBrand(@RequestBody GinBrandDTO ginBrandDTO) {
        ginBrandService.createGinBrand(ginBrandDTO);
        return ResponseEntity.ok("Gin brand added successfully!");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(path = "/uploadimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addGinBrandWithFile(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("sovrapprezzo") double sovrapprezzo,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        String imageUrl = imageUploader.uploadImage(imageFile);

        if (imageUrl == null) {
            return ResponseEntity.badRequest().body("Failed to upload image");
        }

        GinBrandDTO ginBrandDTO = new GinBrandDTO(name, imageUrl, description, sovrapprezzo);
        ginBrandService.createGinBrand(ginBrandDTO);

        return ResponseEntity.ok("Gin brand added successfully with image!");
    }

    @GetMapping("/getall")
    public ResponseEntity<List<GinBrand>> getAllBrands() {
        List<GinBrand> brands = ginBrandService.findAll();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{brandName}")
    public ResponseEntity<GinBrand> getBrandByName(@PathVariable String brandName) {
        GinBrand brand = ginBrandService.findByName(brandName);
        return ResponseEntity.ok(brand);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{brandName}")
    public ResponseEntity<String> deleteBrandByName(@PathVariable String brandName) {
        ginBrandService.deleteBrandByName(brandName);
        return ResponseEntity.ok("Gin brand deleted successfully!");
    }
}
