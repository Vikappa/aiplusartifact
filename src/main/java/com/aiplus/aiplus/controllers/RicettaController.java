package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.Ricetta;
import com.aiplus.aiplus.payloads.DTO.NewRicetta;
import com.aiplus.aiplus.payloads.DTO.RicettaDTO;
import com.aiplus.aiplus.services.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Ricetta> createNewRicetta(@RequestBody NewRicetta newRicetta) {
        Ricetta createdRicetta = ricettaService.createNewRicetta(newRicetta);
        return ResponseEntity.ok(createdRicetta);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<RicettaDTO>> getAllRicette() {
        return ResponseEntity.ok(ricettaService.getAll());
    }
}
