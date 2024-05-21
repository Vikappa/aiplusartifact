package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.movimenti.Ordine;
import com.aiplus.aiplus.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordina")
public class OrdineController {

    @Autowired
    JWTTools jwtTools;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/ordina")
    public ResponseEntity<Ordine> creaOrdine(@RequestHeader(value="Authorization") String token){
        int tavolo = jwtTools.extractTableNumber(token);

        System.out.println("Tavolo N" + tavolo);

        return ResponseEntity.ok().body(new Ordine());
    }
}
