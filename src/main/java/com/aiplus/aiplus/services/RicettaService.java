package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.*;
import com.aiplus.aiplus.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RicettaService {

    private static final Logger logger = LoggerFactory.getLogger(RicettaService.class);

    @Autowired
    private RicettaDAO ricettaDAO;

    @Autowired
    private GinBottleDAO ginBottleDAO;

    @Autowired
    private TonicaDAO tonicaDAO;

    @Autowired
    private GarnishDAO garnishDAO;

    @Autowired
    private GinFlavourDAO ginFlavourDAO;

    @Autowired
    private FlavourDAO flavourDAO;

    @Autowired
    private ExtraDAO extraDAO;

    public List<RicettaDTO> getAll() {
        List<Ricetta> ricette = ricettaDAO.findAll();
        return ricette.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public Ricetta createNewRicetta(NewRicetta newRicetta) {
        logger.info(newRicetta.name());
        logger.info("Creo la ricetta:", newRicetta.name());

        GinFlavour ginFlavour = ginFlavourDAO.findByName(newRicetta.gin_flavour_id());
        Flavour tonica = flavourDAO.findByName(newRicetta.flavour_tonica_id());

        Ricetta ricetta = new Ricetta();
        ricetta.setName(newRicetta.name());
        ricetta.setGinFlavour(ginFlavour);
        ricetta.setTonica(tonica);

        logger.info(newRicetta.gin_flavour_id());
        logger.info(newRicetta.flavour_tonica_id());


        List<ExtraQuantityDTO> extrasDTO = newRicetta.extras();
        List<GarnishQuantityDTO> garnishesDTO = newRicetta.garnishes();

        List<ExtraQuantity> extras = new ArrayList<>();
        List<GarnishQuantity> garnishes = new ArrayList<>();

        for (ExtraQuantityDTO extraDTO : extrasDTO) {
            Extra extra = extraDAO.findByNameAndUM(extraDTO.getExtraId(), extraDTO.getUM())
                    .orElseThrow(() -> {
                        logger.error("Invalid extra ID: {}", extraDTO.getExtraId());
                        return new IllegalArgumentException("Invalid extra ID: " + extraDTO.getExtraId());
                    });
            ExtraQuantity newExtra = new ExtraQuantity();
            newExtra.setExtra(extra);
            newExtra.setRicetta(ricetta);
            newExtra.setQuantity(extraDTO.getQuantity());
            newExtra.setUM(extraDTO.getUM());
            extras.add(newExtra);
        }

        for (GarnishQuantityDTO garnishDTO : garnishesDTO) {
            Guarnizione guarnizione = garnishDAO.findByNameAndUM(garnishDTO.getGuarnizioneId(), garnishDTO.getUM())
                    .orElseThrow(() -> {
                        logger.error("Invalid guarnizione ID: {}", garnishDTO.getGuarnizioneId());
                        return new IllegalArgumentException("Invalid guarnizione ID: " + garnishDTO.getGuarnizioneId());
                    });
            GarnishQuantity newGarnish = new GarnishQuantity();
            newGarnish.setGuarnizione(guarnizione);
            newGarnish.setRicetta(ricetta);
            newGarnish.setQuantity(garnishDTO.getQuantity());
            newGarnish.setUM(garnishDTO.getUM());
            garnishes.add(newGarnish);
        }

        ricetta.setExtras(extras);
        ricetta.setGarnishes(garnishes);

        Ricetta savedRicetta = ricettaDAO.save(ricetta);
        logger.info("New Ricetta created with ID: {}", savedRicetta.getId());
        return savedRicetta;
    }

    private RicettaDTO convertToDTO(Ricetta ricetta) {
        RicettaDTO ricettaDTO = new RicettaDTO();
        ricettaDTO.setId(ricetta.getId());
        ricettaDTO.setName(ricetta.getName());
        ricettaDTO.setGinFlavourName(ricetta.getGinFlavour().getName());
        ricettaDTO.setTonicaName(ricetta.getTonica().getName());
        ricettaDTO.setExtras(ricetta.getExtras().stream().map(extra -> new ExtraQuantityDTO(
                extra.getExtra().getName(),
                extra.getExtra().getName(),
                extra.getQuantity(),
                extra.getUM()
        )).collect(Collectors.toList()));
        ricettaDTO.setGarnishes(ricetta.getGarnishes().stream().map(garnish -> new GarnishQuantityDTO(
                garnish.getGuarnizione().getName(),
                garnish.getGuarnizione().getName(),
                garnish.getQuantity(),
                garnish.getUM()
        )).collect(Collectors.toList()));
        int quantitaPreparabile = calcolaQuantitaPreparabile(ricetta);
        ricettaDTO.setPreparabile(quantitaPreparabile > 0);
        ricettaDTO.setQuantitaPreparabile(quantitaPreparabile);
        return ricettaDTO;
    }

    private int calcolaQuantitaPreparabile(Ricetta ricetta) {
        // Trova la bottiglia di gin con il gusto giusto e calcola quante dosi possono essere preparate
        GinBottle ginBottle = ginBottleDAO.findFirstByGinFlavourAndCurrentVolumeGreaterThanEqual(ricetta.getGinFlavour(), 60);
        if (ginBottle == null) {
            return 0;
        }
        int ginDosi = (int) (ginBottle.getCurrentVolume() / 60);

        // Trova quante bottiglie di tonica sono disponibili
        long tonicaCount = tonicaDAO.countByFlavour(ricetta.getTonica());
        if (tonicaCount == 0) {
            return 0;
        }

        // Trova quante dosi di extra possono essere preparate
        int extraDosi = Integer.MAX_VALUE;
        for (ExtraQuantity extra : ricetta.getExtras()) {
            // Prova a ottenere il primo risultato
            Extra extraInMagazzino = extraDAO.findTop1ByNameAndUMAndQtaExtraGreaterThanEqual(
                    extra.getExtra().getName(),
                    extra.getUM(),
                    extra.getQuantity()
            );

            if (extraInMagazzino == null) {
                // Prova a ottenere tutti i risultati e sommare le quantità
                List<Extra> allExtrasInMagazzino = extraDAO.findByNameAndUMAndQtaExtraGreaterThanEqual(
                        extra.getExtra().getName(),
                        extra.getUM(),
                        1 // qualunque quantità maggiore di 0
                );

                int totalQuantity = 0;
                for (Extra e : allExtrasInMagazzino) {
                    totalQuantity += e.getQtaExtra();
                    if (totalQuantity >= extra.getQuantity()) {
                        break;
                    }
                }

                // Se la somma totale è minore della quantità richiesta, non è possibile soddisfare la richiesta
                if (totalQuantity < extra.getQuantity()) {
                    return 0;
                }

                // Calcola il numero di dosi che possono essere fatte con la quantità totale trovata
                extraDosi = Math.min(extraDosi, totalQuantity / extra.getQuantity());
            } else {
                // Se esiste un risultato che soddisfa la condizione, calcola le dosi normalmente
                extraDosi = Math.min(extraDosi, extraInMagazzino.getQtaExtra() / extra.getQuantity());
            }
        }

        // Trova quante dosi di guarnizioni possono essere preparate
        int garnishDosi = Integer.MAX_VALUE;
        for (GarnishQuantity garnish : ricetta.getGarnishes()) {
            // Prova a ottenere il primo risultato
            Guarnizione garnishInMagazzino = garnishDAO.findTop1ByNameAndUMAndQuantitaGarnishGreaterThanEqual(
                    garnish.getGuarnizione().getName(),
                    garnish.getUM(),
                    garnish.getQuantity()
            );

            if (garnishInMagazzino == null) {
                // Prova a ottenere tutti i risultati e sommare le quantità
                List<Guarnizione> allGarnishesInMagazzino = garnishDAO.findByNameAndUMAndQuantitaGarnishGreaterThanEqual(
                        garnish.getGuarnizione().getName(),
                        garnish.getUM(),
                        1 // qualunque quantità maggiore di 0
                );

                int totalQuantity = 0;
                for (Guarnizione g : allGarnishesInMagazzino) {
                    totalQuantity += g.getQuantitaGarnish();
                    if (totalQuantity >= garnish.getQuantity()) {
                        break;
                    }
                }

                // Se la somma totale è minore della quantità richiesta, non è possibile soddisfare la richiesta
                if (totalQuantity < garnish.getQuantity()) {
                    return 0;
                }

                // Calcola il numero di dosi che possono essere fatte con la quantità totale trovata
                garnishDosi = Math.min(garnishDosi, totalQuantity / garnish.getQuantity());
            } else {
                // Se esiste un risultato che soddisfa la condizione, calcola le dosi normalmente
                garnishDosi = Math.min(garnishDosi, garnishInMagazzino.getQuantitaGarnish() / garnish.getQuantity());
            }
        }

        // Restituisce il numero minimo di dosi che possono essere preparate
        return Math.min(ginDosi, Math.min((int) tonicaCount, Math.min(extraDosi, garnishDosi)));
    }

}
