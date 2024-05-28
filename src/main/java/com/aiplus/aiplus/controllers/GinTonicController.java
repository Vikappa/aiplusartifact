package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.GinTonic;
import com.aiplus.aiplus.payloads.DTO.NewGinTonic;
import com.aiplus.aiplus.services.GinTonicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gintonic")
public class GinTonicController {

    @Autowired
    private GinTonicService ginTonicService;

    @GetMapping("/getAll")
    ResponseEntity<List<GinTonic>> getAll(){
        return ResponseEntity.ok().body(ginTonicService.getAll());
    }

    @PostMapping("/add")
    ResponseEntity<?> addGinTonic(@RequestBody NewGinTonic body){
        return ResponseEntity.ok().body(ginTonicService.add(body));
    }
}
