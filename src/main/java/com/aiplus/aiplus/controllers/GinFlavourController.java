package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import com.aiplus.aiplus.services.GinFlavourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ginflavours")
public class GinFlavourController {

    @Autowired
    GinFlavourService ginFlavourService;

    @GetMapping("/getall")
    public List<GinFlavour> getAllGinFlavours() {
        return ginFlavourService.getAllGinFlavours();
    }
}
