package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.movimenti.Carico;
import com.aiplus.aiplus.payloads.DTO.NewCarico;
import com.aiplus.aiplus.services.CaricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carichi")
public class CaricoController {

    @Autowired
    private CaricoService caricoService;

    @GetMapping
    public ResponseEntity<List<Carico>> getAllCarichi() {
        return caricoService.getAllCarichi();
    }

    @PostMapping
    public ResponseEntity<Carico> addCarico(@RequestBody NewCarico body) {
        return caricoService.addCarico(body);
    }
}
