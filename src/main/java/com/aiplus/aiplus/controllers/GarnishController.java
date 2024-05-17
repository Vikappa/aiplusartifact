package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.payloads.records.GarnishLineShort;
import com.aiplus.aiplus.services.GarnishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/garnish")
public class GarnishController {

    @Autowired
    private GarnishService garnishService;

    @RequestMapping("/getinstorelist")
    public ResponseEntity<List<GarnishLineShort>> getInStoreList() {
        return garnishService.getInStoreList();
    }


}
