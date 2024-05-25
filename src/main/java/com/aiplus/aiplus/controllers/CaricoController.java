package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.movimenti.Carico;
import com.aiplus.aiplus.payloads.DTO.NewCarico;
import com.aiplus.aiplus.services.CaricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getlastcarico")
    public ResponseEntity<Integer> getLastCarico() {
        return caricoService.getLastCarico();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addCarico(@RequestBody NewCarico body, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader;
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return caricoService.addCarico(body, token);
    }


}
