package com.aiplus.aiplus.services;


import com.aiplus.aiplus.entities.stockentities.Extra;
import com.aiplus.aiplus.payloads.records.ExtraRowLineShort;
import com.aiplus.aiplus.repositories.ExtraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtraService {

    @Autowired
    private ExtraDAO extraDao;
    public ResponseEntity<List<ExtraRowLineShort>> getInStoreList() {
        List<Extra> extras = extraDao.findAll();

        List<ExtraRowLineShort> responseBody = new ArrayList<>();

        for (int i = 0; i < extras.size(); i++) {
            ExtraRowLineShort newline = new ExtraRowLineShort(extras.get(i).getId(), extras.get(i).getName(), extras.get(i).getUM());;
        }

        return ResponseEntity.ok(responseBody);
    }
}
