package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.movimenti.Carico;
import com.aiplus.aiplus.payloads.DTO.NewCarico;
import com.aiplus.aiplus.repositories.CaricoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CaricoService {

    @Autowired
    private CaricoDAO caricoDAO;


    public ResponseEntity<List<Carico>> getAllCarichi() {
        return ResponseEntity.ok(caricoDAO.findAll());
    }

    public ResponseEntity<Carico> addCarico(NewCarico body) {
        Carico carico = new Carico();
        carico.setData(LocalDate.now().toString());
        carico.setOperatore(body.operatore());
        carico.setProdotti(body.prodotti());
        return ResponseEntity.ok(caricoDAO.save(carico));
    }
}
