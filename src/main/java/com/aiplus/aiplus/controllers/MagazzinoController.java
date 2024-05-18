package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.payloads.DTO.InventorySummaryDTO;
import com.aiplus.aiplus.services.MagazzinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/magazzino")
public class MagazzinoController {

    @Autowired
    private MagazzinoService magazzinoService;
    @GetMapping("/resume")
    public ResponseEntity<List<Object>> getResume(){
        return magazzinoService.getResume();
    }
}