package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.movimenti.Carico;
import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.NewCarico;
import com.aiplus.aiplus.payloads.DTO.NewProdotto;
import com.aiplus.aiplus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CaricoService {

    @Autowired
    private CaricoDAO caricoDAO;

    @Autowired
    private GinBrandDAO ginBrandDAO;

    @Autowired
    private FlavourDAO flavourDAO;

    @Autowired
    private GinFlavourDAO ginFlavourDAO;

    @Autowired
    private ColoreGuarnizioneDAO coloreGuarnizioneDAO;


    public ResponseEntity<List<Carico>> getAllCarichi() {
        return ResponseEntity.ok(caricoDAO.findAll());
    }

    public ResponseEntity<Carico> addCarico(NewCarico body) {
        Carico carico = new Carico();
        carico.setData(LocalDate.now().toString());
        carico.setOperatore(body.operatore());
        carico.setNote(body.note());

        ArrayList<NewProdotto> prodottiDTO = body.prodotti();
        List<Prodotto> prodottiJPA = new ArrayList<>();

        for (int i = 0; i < prodottiDTO.size(); i++) {
            switch(prodottiDTO.get(i).discriminatorString()){
                case "GIN_BOTTLE":
                    GinBottle newGinBottle = new GinBottle();
                    newGinBottle.setName(prodottiDTO.get(i).name());
                    newGinBottle.setUM(prodottiDTO.get(i).UM());
                    newGinBottle.setBrand(ginBrandDAO.findByName(prodottiDTO.get(i).brandId()));
                    newGinBottle.setProductionDate(prodottiDTO.get(i).productionDate());
                    newGinBottle.setVolume(prodottiDTO.get(i).volume());
                    newGinBottle.setCurrentVolume(prodottiDTO.get(i).volume());
                    newGinBottle.setAlcoholPercentage(prodottiDTO.get(i).alcoholPercentage());
                    newGinBottle.setExpirationDate(prodottiDTO.get(i).expirationDate());
                    newGinBottle.setBatchNumber(prodottiDTO.get(i).batchNumber());
                    newGinBottle.setImageUrl(prodottiDTO.get(i).imageUrl());
                    newGinBottle.setGinFlavour(ginFlavourDAO.findByName(prodottiDTO.get(i).ginFlavourId()));
                    prodottiJPA.add(newGinBottle);
                    break;
                case "TONICA":
                    Tonica newTonica = new Tonica();
                    newTonica.setName(prodottiDTO.get(i).name());
                    newTonica.setUM(prodottiDTO.get(i).UM());
                    newTonica.setFlavour(flavourDAO.findByName(prodottiDTO.get(i).flavourId()));
                    newTonica.setScadenza_tonica(prodottiDTO.get(i).scadenza_tonica());
                    prodottiJPA.add(newTonica);
                    break;
                case "ALIMENTO_EXTRA":
                    Extra newExtra = new Extra();
                    newExtra.setName(prodottiDTO.get(i).name());
                    newExtra.setFlavour(flavourDAO.findByName(prodottiDTO.get(i).flavourId()));
                    newExtra.setScadenza_ingrediente(prodottiDTO.get(i).scadenza_ingrediente());
                    newExtra.setUM(prodottiDTO.get(i).UM());

                    prodottiJPA.add(newExtra);
                    break;
                case "GUARNIZIONE":
                    Guarnizione newGuarnizione = new Guarnizione();
                    newGuarnizione.setName(prodottiDTO.get(i).name());
                    newGuarnizione.setColore(coloreGuarnizioneDAO.findByName(prodottiDTO.get(i).coloreId()));
                    newGuarnizione.setFlavour(flavourDAO.findByName(prodottiDTO.get(i).flavourId()));
                    newGuarnizione.setUM(prodottiDTO.get(i).UM());
                    prodottiJPA.add(newGuarnizione);
                    break;
                default:
                    break;
            }

        }

        carico.setProdotti(prodottiJPA);
        return ResponseEntity.ok(carico);
    }

    public ResponseEntity<Integer> getLastCarico() {
        Integer lastCarico = caricoDAO.findAll().size();
        return ResponseEntity.ok(lastCarico);
    }
}
