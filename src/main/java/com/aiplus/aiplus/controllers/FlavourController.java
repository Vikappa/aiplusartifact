package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.services.FlavourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flavours")
public class FlavourController {

    @Autowired
    private FlavourService flavourService;

    @RequestMapping("/getall")
    public ResponseEntity<List<Flavour>> getAll(){
        return flavourService.getAll();
    }
}