package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.payloads.DTO.TonicaBottleLineShort;
import com.aiplus.aiplus.services.TonicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tonica")
public class TonicaBottleController {

    @Autowired
    private TonicaService tonicaService;

    @GetMapping("/getlineshort")
    public ResponseEntity<List<TonicaBottleLineShort>> getTonicaLineShort(){
        return ResponseEntity.ok().body(tonicaService.getTonicaLineShort());
    }

}
