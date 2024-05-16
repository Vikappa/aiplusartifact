package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.BrandTonica;
import com.aiplus.aiplus.services.BrandTonicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brandtonica")
public class TonicaBrandController {

    @Autowired
    private BrandTonicaService brandTonicaService;

    @GetMapping("/getall")
    public ResponseEntity<List<BrandTonica>> getAll() {
        return brandTonicaService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<BrandTonica> addBrandTonica(@RequestBody BrandTonica brandTonica) {
        BrandTonica savedBrandTonica = brandTonicaService.save(brandTonica);
        return ResponseEntity.ok(savedBrandTonica);
    }
}
