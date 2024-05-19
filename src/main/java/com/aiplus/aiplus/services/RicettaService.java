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
        logger.info("Creating new Ricetta with name: {}", newRicetta.name());

        GinFlavour ginFlavour = ginFlavourDAO.findById(newRicetta.gin_flavour_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid gin flavour ID: " + newRicetta.gin_flavour_id()));
        Flavour tonica = flavourDAO.findById(newRicetta.flavour_tonica_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid flavour ID: " + newRicetta.flavour_tonica_id()));

        Ricetta ricetta = new Ricetta();
        ricetta.setName(newRicetta.name());
        ricetta.setGinFlavour(ginFlavour);
        ricetta.setTonica(tonica);

        List<ExtraQuantityDTO> extrasDTO = newRicetta.extras();
        List<GarnishQuantityDTO> garnishesDTO = newRicetta.garnishes();

        List<ExtraQuantity> extras = new ArrayList<>();
        List<GarnishQuantity> garnishes = new ArrayList<>();

        for (ExtraQuantityDTO extraDTO : extrasDTO) {
            Extra extra = extraDAO.findById(extraDTO.getExtraId())
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
            Guarnizione guarnizione = garnishDAO.findById(garnishDTO.getGuarnizioneId())
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
                extra.getExtra().getId(),
                extra.getExtra().getName(),
                extra.getQuantity(),
                extra.getUM()
        )).collect(Collectors.toList()));
        ricettaDTO.setGarnishes(ricetta.getGarnishes().stream().map(garnish -> new GarnishQuantityDTO(
                garnish.getGuarnizione().getId(),
                garnish.getGuarnizione().getName(),
                garnish.getQuantity(),
                garnish.getUM()
        )).collect(Collectors.toList()));
        ricettaDTO.setPreparabile(isPreparabile(ricetta));
        return ricettaDTO;
    }

    private boolean isPreparabile(Ricetta ricetta) {
        // Controlla se c'è una bottiglia di gin con il gusto giusto e almeno 60 ml di volume rimanente
        GinBottle ginBottle = ginBottleDAO.findFirstByGinFlavourAndCurrentVolumeGreaterThanEqual(ricetta.getGinFlavour(), 60);
        if (ginBottle == null) {
            return false;
        }

        // Controlla se c'è una bottiglia di tonica
        Tonica tonica = tonicaDAO.findFirstByFlavour(ricetta.getTonica());
        if (tonica == null) {
            return false;
        }

        // Controlla se ci sono gli extra richiesti
        for (ExtraQuantity extra : ricetta.getExtras()) {
            Extra extraInMagazzino = extraDAO.findByNameAndUMAndQtaExtraGreaterThanEqual(extra.getExtra().getName(), extra.getUM(), extra.getQuantity());
            if (extraInMagazzino == null) {
                return false;
            }
        }

        // Controlla se ci sono le guarnizioni richieste
        for (GarnishQuantity garnish : ricetta.getGarnishes()) {
            Guarnizione garnishInMagazzino = garnishDAO.findByNameAndUMAndQuantitaGarnishGreaterThanEqual(garnish.getGuarnizione().getName(), garnish.getUM(), garnish.getQuantity());
            if (garnishInMagazzino == null) {
                return false;
            }
        }

        // Se tutto è disponibile, consuma gli ingredienti
        ginBottle.setCurrentVolume(ginBottle.getCurrentVolume() - 60);
        ginBottleDAO.save(ginBottle);

        tonicaDAO.delete(tonica);

        for (ExtraQuantity extra : ricetta.getExtras()) {
            Extra extraInMagazzino = extraDAO.findByNameAndUMAndQtaExtraGreaterThanEqual(extra.getExtra().getName(), extra.getUM(), extra.getQuantity());
            extraInMagazzino.setQtaExtra(extraInMagazzino.getQtaExtra() - extra.getQuantity());
            extraDAO.save(extraInMagazzino);
        }

        for (GarnishQuantity garnish : ricetta.getGarnishes()) {
            Guarnizione garnishInMagazzino = garnishDAO.findByNameAndUMAndQuantitaGarnishGreaterThanEqual(garnish.getGuarnizione().getName(), garnish.getUM(), garnish.getQuantity());
            garnishInMagazzino.setQuantitaGarnish(garnishInMagazzino.getQuantitaGarnish() - garnish.getQuantity());
            garnishDAO.save(garnishInMagazzino);
        }

        return true;
    }
}
