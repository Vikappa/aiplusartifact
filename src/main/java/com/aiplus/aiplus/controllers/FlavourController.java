package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.payloads.DTO.NewFlavour;

import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.services.FlavourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flavours")
public class FlavourController {

    @Autowired
    private FlavourService flavourService;

    @GetMapping("/getall")
    public ResponseEntity<List<Flavour>> getAll() {
        return flavourService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Flavour> addFlavour(@RequestBody NewFlavour body) {
        return flavourService.addFlavour(body);
    }
}
