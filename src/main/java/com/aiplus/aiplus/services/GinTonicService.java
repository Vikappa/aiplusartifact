package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.NewGinTonic;
import com.aiplus.aiplus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GinTonicService {

    @Autowired
    GinTonicDAO ginTonicDAO;

    @Autowired
    TonicaDAO tonicaDAO;

    @Autowired
    ExtraQuantityDAO extraQuantityDAO;

    @Autowired
    GarnishQuantityDAO garnishQuantityDAO;

    @Autowired
    GinBrandDAO ginBrandDAO;

    @Autowired
    GinBottleDAO ginBottleDAO;

    @Autowired
    FlavourDAO flavourDAO;

    @Autowired
    GinFlavourDAO ginFlavourDAO;

    @Autowired
    BrandTonicaDAO brandTonicaDAO;

    public List<GinTonic> getAll(){
        return ginTonicDAO.findAll();
    }

    public ResponseEntity<?> add(NewGinTonic body) {

        GinTonic newGinTonic = new GinTonic();
        newGinTonic.setName(body.name());
        GinBottle ginBottle;
        Tonica tonica;

        boolean ginBottleGot = false;
        boolean tonicaBottleGot = false;

        // Cerca una bottiglia di gin idonea
        Optional<GinBottle> eligibleGinBottle = ginBottleDAO.findByNameAndGinFlavour_NameAndBrand_NameAndCurrentVolumeGreaterThanEqual(
                body.ginBottleName(),
                body.ginFlavourName(),
                body.ginBottleBrandName(),
                60
        );

        if (eligibleGinBottle.isPresent()) {
            ginBottleGot = true;
            ginBottle = eligibleGinBottle.get();
            ginBottle.getGinTonics().add(newGinTonic);
            newGinTonic.setGinBottle(ginBottle);
            ginBottle.setCurrentVolume(ginBottle.getCurrentVolume() - 60);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gin non trovato");
        }

        // Cerca l'ID del gusto della tonica
        Optional<Flavour> flavourOpt = Optional.ofNullable(flavourDAO.findByName(body.tonicaFlavour()));
        if (!flavourOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gusto della tonica non trovato");
        }
        Flavour flavour = flavourOpt.get();

        // Cerca l'ID del brand della tonica
        Optional<BrandTonica> brandOpt = Optional.ofNullable(brandTonicaDAO.findByName(body.tonicaBrand()));
        if (!brandOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand della tonica non trovato");
        }
        BrandTonica brandTonica = brandOpt.get();

        // Cerca una bottiglia di tonica idonea
        Optional<Tonica> eligibleTonica = tonicaDAO.findFirstByNameAndFlavourAndBrandTonicaAndGinTonicIsNull(
                body.tonicaName(),
                flavour,
                brandTonica
        );

        if (eligibleTonica.isPresent()) {
            tonicaBottleGot = true;
            tonica = eligibleTonica.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tonica non trovata");
        }

        if (ginBottleGot && tonicaBottleGot) {
            // Salva prima il GinTonic
            newGinTonic = ginTonicDAO.save(newGinTonic);

            // Aggiorna Tonica con la referenza al GinTonic salvato
            tonica.setGinTonic(newGinTonic);
            newGinTonic.setTonica(tonica);

            ginBottleDAO.save(ginBottle);
            tonicaDAO.save(tonica);
            return ResponseEntity.ok().body(newGinTonic);
        } else {
            return ResponseEntity.unprocessableEntity().body(newGinTonic);
        }
    }

}
