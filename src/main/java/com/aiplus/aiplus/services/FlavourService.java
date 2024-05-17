package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.payloads.DTO.NewFlavour;
import com.aiplus.aiplus.repositories.FlavourDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlavourService {

    @Autowired
    private FlavourDAO flavourDAO;

    public ResponseEntity<List<Flavour>> getAll(){
        return ResponseEntity.ok(flavourDAO.findAll());
    }

    public ResponseEntity<Flavour> addFlavour(NewFlavour body) {
        Flavour flavour = new Flavour();
        flavour.setName(body.name());
        return ResponseEntity.ok(flavourDAO.save(flavour));
    }
}
