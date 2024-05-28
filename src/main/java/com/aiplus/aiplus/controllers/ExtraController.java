package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.payloads.records.ExtraRowLineShort;
import com.aiplus.aiplus.services.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deperibile")
public class ExtraController {

    @Autowired
    private ExtraService extraService;

    @RequestMapping("/getinstorelist")
    public ResponseEntity<List<ExtraRowLineShort>> getInStoreList() {
        return extraService.getInStoreList();
    }

}
