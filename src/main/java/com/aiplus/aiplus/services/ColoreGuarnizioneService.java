package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.ColoreGuarnizione;
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
}
