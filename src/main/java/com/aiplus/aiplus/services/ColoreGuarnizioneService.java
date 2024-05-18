package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.ColoreGuarnizione;
import com.aiplus.aiplus.payloads.DTO.NewColor;
import com.aiplus.aiplus.repositories.ColoreGuarnizioneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColoreGuarnizioneService {

    @Autowired
    private ColoreGuarnizioneDAO coloreGuarnizioneDAO;

    public ResponseEntity<List<ColoreGuarnizione>> getAll() {
        return ResponseEntity.ok(coloreGuarnizioneDAO.findAll());
    }

    public ResponseEntity<ColoreGuarnizione> addColor(NewColor newcolor) {
        ColoreGuarnizione color = new ColoreGuarnizione();
        color.setName(newcolor.name());
        return ResponseEntity.ok(coloreGuarnizioneDAO.save(color));
    }
}
