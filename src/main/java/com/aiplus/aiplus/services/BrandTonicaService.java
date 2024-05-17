package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.BrandTonica;
import com.aiplus.aiplus.payloads.DTO.NewTonicaBrand;
import com.aiplus.aiplus.repositories.BrandTonicaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class BrandTonicaService {


    @Autowired
    private BrandTonicaDAO brandTonicaDAO;

    public ResponseEntity<List<BrandTonica>> getAll() {
        List<BrandTonica> tonicaList = brandTonicaDAO.findAll();
        return ResponseEntity.ok(tonicaList);
    }

    public ResponseEntity<BrandTonica> createBrandTonica(NewTonicaBrand brandTonicaDTO) {
        BrandTonica newBrandTonica = new BrandTonica();
        newBrandTonica.setName(brandTonicaDTO.name());
        newBrandTonica.setDescription(brandTonicaDTO.description());
        return ResponseEntity.ok(brandTonicaDAO.save(newBrandTonica));
    }
}
