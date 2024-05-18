package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.Extra;
import com.aiplus.aiplus.entities.stockentities.Guarnizione;
import com.aiplus.aiplus.payloads.records.ExtraRowLineShort;
import com.aiplus.aiplus.payloads.records.GarnishLineShort;
import com.aiplus.aiplus.repositories.GarnishDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GarnishService {

    @Autowired
    GarnishDAO garnishDAO;

    public ResponseEntity<List<GarnishLineShort>> getInStoreList() {
        List<Guarnizione> garnishes = garnishDAO.findAll();

        return ResponseEntity.ok(garnishes.stream()
                .map(garnish -> new GarnishLineShort(garnish.getName(), garnish.getUM(), garnish.getColore(), garnish.getFlavour()))
                .distinct()
                .collect(Collectors.toList())) ;
    }

}
