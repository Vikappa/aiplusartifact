package com.aiplus.aiplus.services;


import com.aiplus.aiplus.entities.stockentities.Extra;
import com.aiplus.aiplus.payloads.records.ExtraRowLineShort;
import com.aiplus.aiplus.repositories.ExtraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtraService {

    @Autowired
    private ExtraDAO extraDao;
    public ResponseEntity<List<ExtraRowLineShort>> getInStoreList() {
        List<Extra> extras = extraDao.findAll();

        return ResponseEntity.ok(extras.stream()
                .map(extra -> new ExtraRowLineShort(extra.getName(), extra.getUM(), extra.getFlavour()))
                .distinct()
                .collect(Collectors.toList())) ;
    }
}

