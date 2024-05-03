package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.GinBrand;
import com.aiplus.aiplus.payloads.DTO.GinBrandDTO;
import com.aiplus.aiplus.services.GinBrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/admin/ginbrand")
public class GinBrandController {

    @Autowired
    private GinBrandService ginBrandService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addGinBrand(@RequestBody GinBrandDTO ginBrandDTO) {
        ginBrandService.createGinBrand(ginBrandDTO);
        return ResponseEntity.ok("Gin brand added successfully!");
    }


    @GetMapping("/getall")
    public ResponseEntity<List<GinBrand>> getAllBrands() {
        List<GinBrand> brands = ginBrandService.findAll();
        return ResponseEntity.ok(brands);
    }
}

