package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.ExtraQuantityDTO;
import com.aiplus.aiplus.payloads.DTO.GarnishQuantityDTO;
import com.aiplus.aiplus.payloads.DTO.NewRicetta;
import com.aiplus.aiplus.payloads.DTO.RicettaDTO;
import com.aiplus.aiplus.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            List<Extra> extraList = extraDAO.findFirstByNameAndUMAndQtaExtraGreaterThanZero(extraDTO.getExtraId(), extraDTO.getUM());
            if (extraList.isEmpty()) {
                logger.error("Invalid extra ID: {}", extraDTO.getExtraId());
                throw new IllegalArgumentException("Invalid extra ID: " + extraDTO.getExtraId());
            }
            Extra extra = extraList.get(0);
            ExtraQuantity newExtra = new ExtraQuantity();
            newExtra.setExtra(extra);
            newExtra.setRicetta(ricetta);
            newExtra.setQuantity(extraDTO.getQuantity());
            newExtra.setUM(extraDTO.getUM());
            extras.add(newExtra);
        }

        for (GarnishQuantityDTO garnishDTO : garnishesDTO) {
            Optional<Guarnizione> guarnizioneOpt = garnishDAO.findFirstByNameAndUMAndQuantitaGarnishGreaterThanZero(garnishDTO.getGuarnizioneId(), garnishDTO.getUM());
            if (!guarnizioneOpt.isPresent()) {
                logger.error("Invalid guarnizione ID: {}", garnishDTO.getGuarnizioneId());
                throw new IllegalArgumentException("Invalid guarnizione ID: " + garnishDTO.getGuarnizioneId());
            }
            Guarnizione guarnizione = guarnizioneOpt.get();
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
        // Calcola la somma del volume corrente di tutte le bottiglie con il gusto di gin specificato
        Double totalGinVolume = ginBottleDAO.sumCurrentVolumeByGinFlavour(ricetta.getGinFlavour());
        if (totalGinVolume == null || totalGinVolume < 60) {
            return 0;
        }
        int ginDosi = (int) (totalGinVolume / 60);

        // Trova quante bottiglie di tonica sono disponibili
        long tonicaCount = tonicaDAO.countByFlavourAndGinTonicIsNull(ricetta.getTonica());
        if (tonicaCount == 0) {
            return 0;
        }

        // Trova quante dosi di extra possono essere preparate
        int extraDosi = Integer.MAX_VALUE;
        for (ExtraQuantity extra : ricetta.getExtras()) {
            int totalQuantity = 0;
            List<Extra> allExtrasInMagazzino = extraDAO.findByNameAndUMAndQtaExtraGreaterThanEqual(
                    extra.getExtra().getName(),
                    extra.getUM(),
                    1 // qualunque quantità maggiore di 0
            );

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
        }

        // Trova quante dosi di guarnizioni possono essere preparate
        int garnishDosi = Integer.MAX_VALUE;
        for (GarnishQuantity garnish : ricetta.getGarnishes()) {
            int totalQuantity = 0;
            List<Guarnizione> allGarnishesInMagazzino = garnishDAO.findByNameAndUMAndQuantitaGarnishGreaterThanEqual(
                    garnish.getGuarnizione().getName(),
                    garnish.getUM(),
                    1 // qualunque quantità maggiore di 0
            );

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
        }

        // Restituisce il numero minimo di dosi che possono essere preparate
        return Math.min(ginDosi, Math.min((int) tonicaCount, Math.min(extraDosi, garnishDosi)));
    }
}
