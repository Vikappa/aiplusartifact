package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.ColoreGuarnizione;
import com.aiplus.aiplus.payloads.DTO.NewColor;
import com.aiplus.aiplus.services.ColoreGuarnizioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
public class ColoreGuarnizioneController {

    @Autowired
    private ColoreGuarnizioneService coloreGuarnizioneService;

    @GetMapping("/getall")
    public ResponseEntity<List<ColoreGuarnizione>> getAll() {
        return coloreGuarnizioneService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<ColoreGuarnizione> addColor(@RequestBody NewColor newcolor){
        return coloreGuarnizioneService.addColor(newcolor);
    }
}
