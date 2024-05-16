package com.aiplus.aiplus.services;


import com.aiplus.aiplus.repositories.ExtraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtraService {

    @Autowired
    private ExtraDAO extraDao;
    public ResponseEntity<List<String>> getInStoreList() {
        return ResponseEntity.ok(extraDao.getInStoreList());
    }
}
