package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.movimenti.Carico;
import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.NewCarico;
import com.aiplus.aiplus.payloads.DTO.NewProdotto;
import com.aiplus.aiplus.repositories.*;
import jakarta.transaction.Transactional;
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
    private BrandTonicaDAO brandTonicaDAO;

    @Autowired
    private ColoreGuarnizioneDAO coloreGuarnizioneDAO;

    @Transactional
    public ResponseEntity<List<Carico>> getAllCarichi() {
            List<Carico> carichi = caricoDAO.findAll();
            for (Carico carico : carichi) {
                // Accesso alla lista di prodotti per forzarne il caricamento
                carico.getProdotti().size(); // Forza Hibernate a inizializzare la collezione

                for (Prodotto prodotto : carico.getProdotti()) {
                    // Forza il caricamento di altre proprietà lazy se necessario
                    // Esempio: Accesso a collezioni o entità ulteriormente lazy-loaded dentro Prodotto
                    if (prodotto instanceof GinBottle) {
                        ((GinBottle) prodotto).getBrand().getName(); // Assumendo che brand sia lazy-loaded
                    }
                }
            }

        return ResponseEntity.ok(carichi);
    }

    @Transactional
    public ResponseEntity<Carico> addCarico(NewCarico body) {
        Carico carico = new Carico();
        carico.setData(LocalDate.now().toString());
        carico.setOperatore(body.operatore());
        carico.setNote(body.note());

        ArrayList<NewProdotto> prodottiDTO = body.prodotti();
        List<Prodotto> prodottiJPA = new ArrayList<>();
        for (int i = 0; i < prodottiDTO.size(); i++) {
        Prodotto prodotto;
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
                    prodotto = newGinBottle;
                    prodottiJPA.add(prodotto);
                    break;
                case "TONICA":
                    Tonica newTonica = new Tonica();
                    newTonica.setName(prodottiDTO.get(i).name());
                    newTonica.setUM(prodottiDTO.get(i).UM());
                    newTonica.setFlavour(flavourDAO.findByName(prodottiDTO.get(i).flavourId()));
                    newTonica.setScadenza_tonica(prodottiDTO.get(i).scadenza_tonica());
                    newTonica.setBrandTonica(brandTonicaDAO.findByName(prodottiDTO.get(i).brand_tonica_name()));
                    prodotto = newTonica;
                    prodottiJPA.add(prodotto);
                    break;
                case "ALIMENTO_EXTRA":
                    Extra newExtra = new Extra();
                    newExtra.setName(prodottiDTO.get(i).name());
                    newExtra.setFlavour(flavourDAO.findByName(prodottiDTO.get(i).flavourId()));
                    newExtra.setScadenza_ingrediente(prodottiDTO.get(i).scadenza_ingrediente());
                    newExtra.setUM(prodottiDTO.get(i).UM());
                    prodotto = newExtra;
                    prodottiJPA.add(prodotto);
                    break;
                case "GUARNIZIONE":
                    Guarnizione newGuarnizione = new Guarnizione();
                    newGuarnizione.setName(prodottiDTO.get(i).name());
                    newGuarnizione.setColore(coloreGuarnizioneDAO.findByName(prodottiDTO.get(i).coloreId()));
                    newGuarnizione.setFlavour(flavourDAO.findByName(prodottiDTO.get(i).flavourId()));
                    newGuarnizione.setUM(prodottiDTO.get(i).UM());
                    prodotto = newGuarnizione;
                    prodottiJPA.add(prodotto);
                    break;
                default:
                    break;
            }

        }

        for (int y = 0; y < prodottiJPA.size(); y++){
            prodottiJPA.get(y).setCarico(carico);
        }

        carico.setProdotti(prodottiJPA);
        return ResponseEntity.ok(caricoDAO.save(carico));
    }

    public ResponseEntity<Integer> getLastCarico() {
        Integer lastCarico = caricoDAO.findAll().size();
        return ResponseEntity.ok(lastCarico);
    }
}
