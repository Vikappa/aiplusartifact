package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.movimenti.ORDER_STATUS;
import com.aiplus.aiplus.entities.movimenti.Ordine;
import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.NewExtraQuantity;
import com.aiplus.aiplus.payloads.DTO.NewGarnishQuantity;
import com.aiplus.aiplus.payloads.DTO.NewOrdine;
import com.aiplus.aiplus.payloads.records.ExtraAvailabilityDTO;
import com.aiplus.aiplus.payloads.records.GarnishAvailabilityDTO;
import com.aiplus.aiplus.payloads.records.OrdineIdPayoad;
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

    public void processRequestExtras(List<NewExtraQuantity> requestExtras) {
        for (NewExtraQuantity requestExtra : requestExtras) {
            List<Extra> allQueryResult = extraDAO.findByNameAndUM(requestExtra.extraName(), requestExtra.um());

            if (allQueryResult.isEmpty()) {
                // Gestisci il caso in cui non ci sono risultati
                throw new IllegalArgumentException("No Extra found for name: " + requestExtra.extraName() + " and UM: " + requestExtra.um());
            }

            // Logica per elaborare i risultati trovati
            for (Extra extra : allQueryResult) {
                // Fai qualcosa con ogni `Extra`
                System.out.println("Found Extra: " + extra.getName() + ", UM: " + extra.getUM());
            }
        }
    }

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

        List<ExtraAvailabilityDTO> extraDisponibili = new ArrayList<>();
        List<GarnishAvailabilityDTO> garnishDisponibili = new ArrayList<>();

        // Gestione degli Extra
        for (NewExtraQuantity requestExtra : requestExtras) {
            List<Extra> allQueryResult = extraDAO.findByNameAndUM(requestExtra.extraName(), requestExtra.um());
            for (Extra currentExtra : allQueryResult) {
                ExtraAvailabilityDTO newExtraAvailability = new ExtraAvailabilityDTO();
                newExtraAvailability.setIdReference(currentExtra.getId());

                // Calcola la quantità disponibile al netto degli ExtraQuantity associati
                int totalUsedQuantity = currentExtra.getExtraQuantities().stream()
                        .mapToInt(ExtraQuantity::getQuantity)
                        .sum();
                newExtraAvailability.setAvailableQuantity(currentExtra.getQtaExtra() - totalUsedQuantity);
                extraDisponibili.add(newExtraAvailability);
            }
        }

        // Implementa la logica per selezionare gli extra idonei basandoti su extraDisponibili
        for (NewExtraQuantity requestExtra : requestExtras) {
            int quantityNeeded = requestExtra.quantity();
            for (ExtraAvailabilityDTO extraDisponibile : extraDisponibili) {
                if (quantityNeeded <= 0) break;
                if (extraDisponibile.getAvailableQuantity() > 0) {
                    int usedQuantity = Math.min(quantityNeeded, extraDisponibile.getAvailableQuantity());
                    ExtraQuantity newExtraQuantity = new ExtraQuantity();
                    newExtraQuantity.setExtra(extraDAO.findById(extraDisponibile.getIdReference()).get());
                    newExtraQuantity.setQuantity(usedQuantity);
                    newExtraQuantity.setUM(requestExtra.um());
                    elegibleExtras.add(newExtraQuantity);
                    extraDisponibile.setAvailableQuantity(extraDisponibile.getAvailableQuantity() - usedQuantity);
                    quantityNeeded -= usedQuantity;
                }
            }
            if (quantityNeeded > 0) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                        "Non ci sono abbastanza extra disponibili: " + requestExtra.extraName());
            }
        }

        // Gestione dei Garnish
        for (NewGarnishQuantity requestGarnish : requestGarnishes) {
            List<Guarnizione> allQueryResult = garnishDAO.findAllByNameAndUM(requestGarnish.garnishName(), requestGarnish.um());
            for (Guarnizione currentGarnish : allQueryResult) {
                GarnishAvailabilityDTO newGarnishAvailability = new GarnishAvailabilityDTO();
                newGarnishAvailability.setReferenceId(currentGarnish.getId());

                // Calcola la quantità disponibile al netto dei GarnishQuantity associati
                int totalUsedQuantity = currentGarnish.getGarnishQuantities().stream()
                        .mapToInt(GarnishQuantity::getQuantity)
                        .sum();
                newGarnishAvailability.setAvailableQuantity(currentGarnish.getQuantitaGarnish() - totalUsedQuantity);
                garnishDisponibili.add(newGarnishAvailability);
            }
        }

        // Implementa la logica per selezionare i garnish idonei basandoti su garnishDisponibili
        for (NewGarnishQuantity requestGarnish : requestGarnishes) {
            int quantityNeeded = requestGarnish.quantity();
            for (GarnishAvailabilityDTO garnishDisponibile : garnishDisponibili) {
                if (quantityNeeded <= 0) break;
                if (garnishDisponibile.getAvailableQuantity() > 0) {
                    int usedQuantity = Math.min(quantityNeeded, garnishDisponibile.getAvailableQuantity());
                    GarnishQuantity newGarnishQuantity = new GarnishQuantity();
                    newGarnishQuantity.setGuarnizione(garnishDAO.findById(garnishDisponibile.getReferenceId()).get());
                    newGarnishQuantity.setQuantity(usedQuantity);
                    newGarnishQuantity.setUM(requestGarnish.um());
                    elegibleGarnishes.add(newGarnishQuantity);
                    garnishDisponibile.setAvailableQuantity(garnishDisponibile.getAvailableQuantity() - usedQuantity);
                    quantityNeeded -= usedQuantity;
                }
            }
            if (quantityNeeded > 0) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                        "Non ci sono abbastanza garnish disponibili: " + requestGarnish.garnishName());
            }
        }

        // Completa l'ordine
        if (ginBottleGot && tonicaBottleGot) {
            ordine.setStatus(ORDER_STATUS.SENT);
            ordine.setNTavolo(tavolo);

            // Associa il gin tonic all'ordine
            ordine.setGinTonic(newGinTonic);
            newGinTonic.setOrdine(ordine);
            newGinTonic.setExtras(elegibleExtras);
            newGinTonic.setGarnishes(elegibleGarnishes);
            newGinTonic.setTonica(tonica);

            newGinTonic.setFinalPrice(newGinTonic.getBASE_PRICE() + newGinTonic.getGinBottle().getBrand().getSovrapprezzo() + (newGinTonic.getExtras().size() * 0.5) + (newGinTonic.getGarnishes().size()*0.25) );

            tonica.setGinTonic(newGinTonic);

            ordine = ordineDAO.save(ordine);
            ginBottleDAO.save(ginBottle);
            ginTonicDAO.save(newGinTonic);
            tonicaDAO.save(tonica);

            // Salva le quantita' di extra e garnish
            for (ExtraQuantity extraQuantity : elegibleExtras) {
                extraQuantity.setGinTonic(newGinTonic);
                extraQuantityDAO.save(extraQuantity);
            }
            for (GarnishQuantity garnishQuantity : elegibleGarnishes) {
                garnishQuantity.setGinTonic(newGinTonic);
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

    public Object setToPreparato(OrdineIdPayoad id) {
        Ordine ordine = ordineDAO.findById(id.ordineId()).get();
        ordine.setStatus(ORDER_STATUS.DELIVERED);
        ordineDAO.save(ordine);
        return "Ordine " + id + " set to DELIVERED";
    }
}
