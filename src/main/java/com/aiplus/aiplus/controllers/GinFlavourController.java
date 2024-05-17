package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import com.aiplus.aiplus.payloads.DTO.NewGinFlavour;
import com.aiplus.aiplus.services.GinFlavourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ginflavours")
public class GinFlavourController {

    @Autowired
    GinFlavourService ginFlavourService;

    @GetMapping("/getall")
    public List<GinFlavour> getAllGinFlavours() {
        return ginFlavourService.getAllGinFlavours();
    }

    @PostMapping("/add")
    public ResponseEntity<GinFlavour> addGinFlavour(@RequestBody NewGinFlavour ginFlavourDTO) {
        return ginFlavourService.addFlavour(ginFlavourDTO);
    }
}
