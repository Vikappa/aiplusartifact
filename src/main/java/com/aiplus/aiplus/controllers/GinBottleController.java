package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.payloads.DTO.GinBottleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aiplus.aiplus.services.GinBottleService;

@RestController
@RequestMapping("/admin/ginbottle")
public class GinBottleController {

    @Autowired
    private GinBottleService ginBottleService;

    @PostMapping("/add")
    public ResponseEntity<String> addGinBottle(@RequestBody GinBottleDTO ginBottleDto) {
        ginBottleService.addGinBottle(ginBottleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Gin bottle added successfully!");
    }
}
