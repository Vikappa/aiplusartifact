package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.movimenti.Carico;
import com.aiplus.aiplus.payloads.DTO.NewCarico;
import com.aiplus.aiplus.services.CaricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/getlastcarico")
    public ResponseEntity<Integer> getLastCarico() {
        return caricoService.getLastCarico();
    }

    @PostMapping
    public ResponseEntity<Carico> addCarico(@RequestBody NewCarico body) {
        System.out.println("Received NewCarico request: {}" + body.toString());
        try {
            return caricoService.addCarico(body);
        } catch (Exception e) {
            System.out.println("Error processing NewCarico" + body.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
