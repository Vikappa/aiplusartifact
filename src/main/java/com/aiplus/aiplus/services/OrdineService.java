package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.movimenti.ORDER_STATUS;
import com.aiplus.aiplus.entities.movimenti.Ordine;
import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.ExtraQuantityDTO;
import com.aiplus.aiplus.payloads.DTO.NewExtraQuantity;
import com.aiplus.aiplus.payloads.DTO.NewGarnishQuantity;
import com.aiplus.aiplus.payloads.DTO.NewOrdine;
import com.aiplus.aiplus.payloads.records.ExtraAvailabilityDTO;
import com.aiplus.aiplus.payloads.records.GarnishAvailabilityDTO;
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

        List<NewExtraQuantity> requestExtras = body.gintonic().extras();
        List<NewGarnishQuantity> requestGarnishes = body.gintonic().garnishes();

        // Gestione degli Extra
        for (NewExtraQuantity requestExtra : requestExtras) {
            double requestExtraQuantity = requestExtra.quantity();
            List<ExtraAvailabilityDTO> availableExtras = extraDAO.findAvailableExtras(
                    requestExtra.extraName(), requestExtra.um(), requestExtra.quantity());

            for (ExtraAvailabilityDTO dto : availableExtras) {
                if (requestExtraQuantity <= 0) break;

                Extra extra = dto.getExtra();
                int availableQuantity = dto.getAvailableQuantity();

                int usedQuantity = (int) Math.min(requestExtraQuantity, availableQuantity);
                requestExtraQuantity -= usedQuantity;

                ExtraQuantity newExtraQuantity = new ExtraQuantity();
                newExtraQuantity.setExtra(extra);
                newExtraQuantity.setGinTonic(newGinTonic);
                newExtraQuantity.setQuantity(usedQuantity);
                newExtraQuantity.setUM(requestExtra.um());

                dto.setAvailableQuantity(availableQuantity - usedQuantity);
                elegibleExtras.add(newExtraQuantity);
            }

            if (requestExtraQuantity > 0) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                        "Non ci sono abbastanza extra disponibili: " + requestExtra.extraName());
            }
        }

        // Gestione dei Garnish
        for (NewGarnishQuantity requestGarnish : requestGarnishes) {
            double requestGarnishQuantity = requestGarnish.quantity();
            List<GarnishAvailabilityDTO> availableGarnishes = garnishDAO.findAvailableGarnishes(
                    requestGarnish.garnishName(), requestGarnish.um(), requestGarnish.quantity());

            for (GarnishAvailabilityDTO dto : availableGarnishes) {
                if (requestGarnishQuantity <= 0) break;

                Guarnizione garnish = dto.getGuarnizione();
                int availableQuantity = dto.getAvailableQuantity();

                int usedQuantity = (int) Math.min(requestGarnishQuantity, availableQuantity);
                requestGarnishQuantity -= usedQuantity;

                GarnishQuantity newGarnishQuantity = new GarnishQuantity();
                newGarnishQuantity.setGuarnizione(garnish);
                newGarnishQuantity.setGinTonic(newGinTonic);
                newGarnishQuantity.setQuantity(usedQuantity);
                newGarnishQuantity.setUM(requestGarnish.um());

                dto.setAvailableQuantity(availableQuantity - usedQuantity);
                elegibleGarnishes.add(newGarnishQuantity);
            }

            if (requestGarnishQuantity > 0) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                        "Non ci sono abbastanza garnish disponibili: " + requestGarnish.garnishName());
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

            // Salva le quantita' di extra e garnish
            for (ExtraQuantity extraQuantity : elegibleExtras) {
                extraQuantityDAO.save(extraQuantity);
            }
            for (GarnishQuantity garnishQuantity : elegibleGarnishes) {
                garnishQuantityDAO.save(garnishQuantity);
            }

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
