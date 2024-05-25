package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.movimenti.ORDER_STATUS;
import com.aiplus.aiplus.entities.movimenti.Ordine;
import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.ExtraQuantityDTO;
import com.aiplus.aiplus.payloads.DTO.NewExtraQuantity;
import com.aiplus.aiplus.payloads.DTO.NewGarnishQuantity;
import com.aiplus.aiplus.payloads.DTO.NewOrdine;
import com.aiplus.aiplus.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdineService {

    @Autowired
    OrdineDAO ordineDAO;

    @Autowired
    private GinBottleDAO ginBottleDAO;

    @Autowired
    private FlavourDAO flavourDAO;

    @Autowired
    private BrandTonicaDAO brandTonicaDAO;

    @Autowired
    private TonicaDAO tonicaDAO;

    @Autowired
    private GinTonicDAO ginTonicDAO;

    @Autowired
    private ExtraDAO extraDAO;

    @Autowired
    private GarnishDAO garnishDAO;

    @Autowired
    ExtraQuantityDAO extraQuantityDAO;

    @Autowired
    GarnishQuantityDAO garnishQuantityDAO;

    public ResponseEntity<?> creaOrdine(int tavolo, NewOrdine body) {
        Ordine ordine = new Ordine();
        GinTonic newGinTonic = new GinTonic();
        newGinTonic.setName(body.gintonic().name());
        GinBottle ginBottle;
        Tonica tonica;

        boolean ginBottleGot = false;
        boolean tonicaBottleGot = false;

        // Cerca una lista di bottiglie di gin idonee
        List<GinBottle> eligibleGinBottles = ginBottleDAO.findByNameAndGinFlavour_NameAndBrand_NameAndCurrentVolumeGreaterThanEqual(
                body.gintonic().ginBottleName(),
                body.gintonic().ginFlavourName(),
                body.gintonic().ginBottleBrandName(),
                60
        );

        if (!eligibleGinBottles.isEmpty()) {
            ginBottleGot = true;
            ginBottle = eligibleGinBottles.get(0); // Seleziona la prima bottiglia disponibile
            ginBottle.setCurrentVolume(ginBottle.getCurrentVolume() - 60);
            ginBottle.getGinTonics().add(newGinTonic); // Aggiungi il nuovo gin tonic alla lista esistente
            newGinTonic.setGinBottle(ginBottle);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gin non trovato");
        }

        // Cerca l'ID del gusto della tonica
        Optional<Flavour> flavourOpt = Optional.ofNullable(flavourDAO.findByName(body.gintonic().tonicaFlavour()));
        if (!flavourOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gusto della tonica non trovato");
        }
        Flavour flavour = flavourOpt.get();

        // Cerca l'ID del brand della tonica
        Optional<BrandTonica> brandOpt = Optional.ofNullable(brandTonicaDAO.findByName(body.gintonic().tonicaBrand()));
        if (!brandOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand della tonica non trovato");
        }
        BrandTonica brandTonica = brandOpt.get();

        // Cerca una bottiglia di tonica idonea
        Optional<Tonica> eligibleTonica = tonicaDAO.findFirstByNameAndFlavourAndBrandTonicaAndGinTonicIsNull(
                body.gintonic().tonicaName(),
                flavour,
                brandTonica
        );

        if (eligibleTonica.isPresent()) {
            tonicaBottleGot = true;
            tonica = eligibleTonica.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tonica non trovata");
        }

        List<ExtraQuantity> elegibleExtras = new ArrayList<>();
        List<GarnishQuantity> elegibleGarnishes = new ArrayList<>();

        // Cerca i garnish elegibili
        for (NewGarnishQuantity garnishRequest : body.gintonic().garnishes()) {
            List<Guarnizione> availableGarnishes = garnishDAO.findByNameAndUMAndQuantitaGarnishGreaterThanEqual(garnishRequest.garnishName(), garnishRequest.um(), garnishRequest.quantity());
            int quantityNeeded = garnishRequest.quantity();
            List<Guarnizione> selectedGarnishes = new ArrayList<>();

            for (Guarnizione garnish : availableGarnishes) {
                if (quantityNeeded <= 0) break;
                selectedGarnishes.add(garnish);
                quantityNeeded -= garnish.getQuantitaGarnish();
            }

            if (quantityNeeded > 0) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Non ci sono abbastanza garnish disponibili: " + garnishRequest.garnishName());
            }

            for (Guarnizione garnish : selectedGarnishes) {
                GarnishQuantity garnishQuantity = new GarnishQuantity();
                garnishQuantity.setGuarnizione(garnish);
                garnishQuantity.setGinTonic(newGinTonic);
                garnishQuantity.setQuantity(Math.min(garnishRequest.quantity(), garnish.getQuantitaGarnish()));
                elegibleGarnishes.add(garnishQuantity);
            }
        }

        if (ginBottleGot && tonicaBottleGot) {
            ordine.setStatus(ORDER_STATUS.SENT);
            ordine.setNTavolo(tavolo);

            // Associa il gin tonic all'ordine
            ordine.setGinTonic(newGinTonic);
            newGinTonic.setOrdine(ordine);
            newGinTonic.setExtras(elegibleExtras);
            newGinTonic.setGarnishes(elegibleGarnishes);
            newGinTonic.setTonica(tonica);

            tonica.setGinTonic(newGinTonic);

            ordine = ordineDAO.save(ordine);
            ginBottleDAO.save(ginBottle);
            ginTonicDAO.save(newGinTonic);
            tonicaDAO.save(tonica);

            return ResponseEntity.ok().body(ordine);
        } else {
            return ResponseEntity.unprocessableEntity().body("Gin or Tonica not found");
        }
    }



    @Transactional
    public List<Ordine> getAll() {
        List<Ordine> ordini = ordineDAO.findAll();
        for (Ordine ordine : ordini) {
            GinTonic ginTonic = ordine.getGinTonic();
            if (ginTonic != null) {
                ginTonic.getGinBottle(); // Inizializza il ginBottle
                ginTonic.getTonica(); // Inizializza il tonica
                ginTonic.getExtras().size(); // Inizializza la lista di extras
                ginTonic.getGarnishes().size(); // Inizializza la lista di garnishes
            }
        }
        return ordini;
    }
}
