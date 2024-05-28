package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.movimenti.Carico;
import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.payloads.DTO.NewCarico;
import com.aiplus.aiplus.payloads.DTO.NewProdotto;
import com.aiplus.aiplus.repositories.*;
import com.aiplus.aiplus.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CaricoService {

    private static final Logger logger = LoggerFactory.getLogger(CaricoService.class);

    @Autowired
    private CaricoDAO caricoDAO;

    @Autowired
    private GinBrandDAO ginBrandDAO;

    @Autowired
    private GinFlavourDAO ginFlavourDAO;

    @Autowired
    private FlavourDAO flavourDAO;

    @Autowired
    private BrandTonicaDAO brandTonicaDAO;

    @Autowired
    private ColoreGuarnizioneDAO coloreGuarnizioneDAO;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserDAO userDAO;

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    @Transactional
    public ResponseEntity<List<Carico>> getAllCarichi() {
        List<Carico> carichi = caricoDAO.findAll();
        for (Carico carico : carichi) {
            carico.getProdotti().size();
            for (Prodotto prodotto : carico.getProdotti()) {
                if (prodotto instanceof GinBottle) {
                    ((GinBottle) prodotto).getBrand().getName();
                }
            }
        }
        return ResponseEntity.ok(carichi);
    }

    public ResponseEntity<Integer> getLastCarico() {
        Integer lastCarico = caricoDAO.findAll().size();
        return ResponseEntity.ok(lastCarico);
    }
    @Transactional
    public ResponseEntity<?> addCarico(NewCarico body, String accessoToken) {
        logger.debug("Starting addCarico with body: {}", body);
        try {
            // Estrarre l'ID dell'utente dal token JWT
            String utenteId = jwtTools.extractIdFromToken(accessoToken);
            logger.debug("Verifying operatore with ID: {}", utenteId);

            // Verificare se l'utente esiste
            User operatore = userDAO.findById(UUID.fromString(utenteId))
                    .orElseThrow(() -> new RuntimeException("Operatore not found: " + utenteId));

            Carico carico = new Carico();
            carico.setData(LocalDate.now().toString());
            carico.setOperatore(operatore);
            carico.setNote(body.note());

            ArrayList<NewProdotto> prodottiDTO = body.prodotti();
            List<Prodotto> prodottiJPA = new ArrayList<>();
            for (NewProdotto prodottoDTO : prodottiDTO) {
                Prodotto prodotto;
                switch (prodottoDTO.discriminatorString()) {
                    case "GIN_BOTTLE":
                        GinBottle newGinBottle = new GinBottle();
                        newGinBottle.setName(prodottoDTO.name());
                        newGinBottle.setUM(prodottoDTO.UM());
                        GinBrand ginBrand = ginBrandDAO.findByName(prodottoDTO.brandId());
                        if (ginBrand == null) {
                            logger.error("GinBrand not found: {}", prodottoDTO.brandId());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GinBrand not found: " + prodottoDTO.brandId());
                        }
                        newGinBottle.setBrand(ginBrand);
                        newGinBottle.setProductionDate(prodottoDTO.productionDate());
                        newGinBottle.setVolume(prodottoDTO.volume());
                        newGinBottle.setCurrentVolume(prodottoDTO.volume());
                        newGinBottle.setAlcoholPercentage(prodottoDTO.alcoholPercentage());
                        newGinBottle.setExpirationDate(prodottoDTO.expirationDate());
                        newGinBottle.setBatchNumber(prodottoDTO.batchNumber());
                        GinFlavour ginFlavour = ginFlavourDAO.findByName(prodottoDTO.ginFlavourId());
                        if (ginFlavour == null) {
                            logger.error("GinFlavour not found: {}", prodottoDTO.ginFlavourId());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GinFlavour not found: " + prodottoDTO.ginFlavourId());
                        }
                        newGinBottle.setGinFlavour(ginFlavour);
                        prodotto = newGinBottle;
                        prodottiJPA.add(prodotto);
                        break;
                    case "TONICA":
                        Tonica newTonica = new Tonica();
                        newTonica.setName(prodottoDTO.name());
                        newTonica.setUM(prodottoDTO.UM());
                        Flavour flavour = flavourDAO.findByName(prodottoDTO.flavourId());
                        if (flavour == null) {
                            logger.error("Flavour not found: {}", prodottoDTO.flavourId());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flavour not found: " + prodottoDTO.flavourId());
                        }
                        newTonica.setFlavour(flavour);
                        newTonica.setScadenza_tonica(prodottoDTO.scadenza_tonica());
                        BrandTonica brandTonica = brandTonicaDAO.findByName(prodottoDTO.brand_tonica_name());
                        if (brandTonica == null) {
                            logger.error("BrandTonica not found: {}", prodottoDTO.brand_tonica_name());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BrandTonica not found: " + prodottoDTO.brand_tonica_name());
                        }
                        newTonica.setBrandTonica(brandTonica);
                        prodotto = newTonica;
                        prodottiJPA.add(prodotto);
                        break;
                    case "ALIMENTO_EXTRA":
                        Extra newExtra = new Extra();
                        newExtra.setName(prodottoDTO.name());
                        Flavour extraFlavour = flavourDAO.findByName(prodottoDTO.flavourId());
                        if (extraFlavour == null) {
                            logger.error("Flavour not found: {}", prodottoDTO.flavourId());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flavour not found: " + prodottoDTO.flavourId());
                        }
                        newExtra.setFlavour(extraFlavour);
                        newExtra.setScadenza_ingrediente(prodottoDTO.scadenza_ingrediente());
                        newExtra.setUM(prodottoDTO.UM());
                        newExtra.setQtaExtra(prodottoDTO.qtaExtra());
                        prodotto = newExtra;
                        prodottiJPA.add(prodotto);
                        break;
                    case "GUARNIZIONE":
                        Guarnizione newGuarnizione = new Guarnizione();
                        newGuarnizione.setName(prodottoDTO.name());
                        ColoreGuarnizione coloreGuarnizione = coloreGuarnizioneDAO.findByName(prodottoDTO.coloreId());
                        if (coloreGuarnizione == null) {
                            logger.error("ColoreGuarnizione not found: {}", prodottoDTO.coloreId());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ColoreGuarnizione not found: " + prodottoDTO.coloreId());
                        }
                        newGuarnizione.setColore(coloreGuarnizione);
                        Flavour garnishFlavour = flavourDAO.findByName(prodottoDTO.flavourId());
                        if (garnishFlavour == null) {
                            logger.error("Flavour not found: {}", prodottoDTO.flavourId());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flavour not found: " + prodottoDTO.flavourId());
                        }
                        newGuarnizione.setFlavour(garnishFlavour);
                        newGuarnizione.setUM(prodottoDTO.UM());
                        newGuarnizione.setQuantitaGarnish(prodottoDTO.quantitaGarnish());
                        prodotto = newGuarnizione;
                        prodottiJPA.add(prodotto);
                        break;
                    default:
                        logger.warn("Unknown product type: {}", prodottoDTO.discriminatorString());
                        break;
                }
            }

            for (Prodotto prodotto : prodottiJPA) {
                prodotto.setCarico(carico);
            }

            carico.setProdotti(prodottiJPA);
            caricoDAO.save(carico);
            logger.debug("Successfully saved carico: {}", carico);
            return ResponseEntity.ok(carico);
        } catch (Exception e) {
            logger.error("Error while saving carico", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage() + "\n" + getStackTrace(e));
        }
    }
}
