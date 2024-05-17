package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.ColoreGuarnizione;
import com.aiplus.aiplus.services.ColoreGuarnizioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
