package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.movimenti.Ordine;
import com.aiplus.aiplus.payloads.DTO.NewOrdine;
import com.aiplus.aiplus.repositories.OrdineDAO;
import com.aiplus.aiplus.security.JWTTools;
import com.aiplus.aiplus.services.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordina")
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    @Autowired
    JWTTools jwtTools;

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/ordina")
    public ResponseEntity<?> creaOrdine(@RequestHeader(value="Authorization") String token, @RequestBody NewOrdine body){
        int tavolo = jwtTools.extractTableNumber(token);

        return ordineService.creaOrdine(tavolo,body);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARMAN')")
    @GetMapping("/getall")
    public ResponseEntity<List<Ordine>> getAll(){
        return ordineService.getAll();
    }

}
