package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import com.aiplus.aiplus.payloads.DTO.NewGinFlavour;
import com.aiplus.aiplus.repositories.GinFlavourDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GinFlavourService {

    @Autowired
    private GinFlavourDAO ginFlavourDAO;
    public List<GinFlavour> getAllGinFlavours() {
        return ginFlavourDAO.findAll();
    }

    public ResponseEntity<GinFlavour> addFlavour(NewGinFlavour ginFlavourDTO) {
        GinFlavour ginFlavour = new GinFlavour();
        ginFlavour.setName(ginFlavourDTO.name());
        return ResponseEntity.ok(ginFlavourDAO.save(ginFlavour));


    }
}
