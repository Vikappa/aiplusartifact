package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.GinBottle;
import com.aiplus.aiplus.payloads.DTO.GinBottleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aiplus.aiplus.services.GinBottleService;

@RestController
@RequestMapping("/ginbottle")
public class GinBottleController {

    @Autowired
    private GinBottleService ginBottleService;

    @PostMapping("/add")
    public ResponseEntity<String> addGinBottle(@RequestBody GinBottleDTO ginBottleDto) {
        ginBottleService.addGinBottle(ginBottleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Gin bottle added successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GinBottle> getGinBottle(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ginBottleService.getGinBottle(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<Iterable<GinBottle>> getAllGinBottles() {
        return ResponseEntity.status(HttpStatus.OK).body(ginBottleService.getAllGinBottles());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGinBottle(@PathVariable("id") long id) {
        ginBottleService.deleteGinBottle(id);
        return ResponseEntity.status(HttpStatus.OK).body("Gin bottle deleted successfully!");
    }
}
