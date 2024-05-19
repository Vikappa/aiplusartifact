package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.*;
import com.aiplus.aiplus.payloads.DTO.*;
import com.aiplus.aiplus.repositories.*;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
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
            Extra extra = extraDAO.findById(extraDTO.extraId())
                    .orElseThrow(() -> {
                        logger.error("Invalid extra ID: {}", extraDTO.extraId());
                        return new IllegalArgumentException("Invalid extra ID: " + extraDTO.extraId());
                    });
            ExtraQuantity newExtra = new ExtraQuantity();
            newExtra.setExtra(extra);
            newExtra.setRicetta(ricetta);
            newExtra.setQuantity(extraDTO.quantity());
            newExtra.setUM(extraDTO.UM());
            extras.add(newExtra);
        }

        for (GarnishQuantityDTO garnishDTO : garnishesDTO) {
            Guarnizione guarnizione = garnishDAO.findById(garnishDTO.guarnizioneId())
                    .orElseThrow(() -> {
                        logger.error("Invalid guarnizione ID: {}", garnishDTO.guarnizioneId());
                        return new IllegalArgumentException("Invalid guarnizione ID: " + garnishDTO.guarnizioneId());
                    });
            GarnishQuantity newGarnish = new GarnishQuantity();
            newGarnish.setGuarnizione(guarnizione);
            newGarnish.setRicetta(ricetta);
            newGarnish.setQuantity(garnishDTO.quantity());
            newGarnish.setUM(garnishDTO.UM());
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
        ricettaDTO.setGinFlavour(ricetta.getGinFlavour().getName());
        ricettaDTO.setTonica(ricetta.getTonica().getName());
        ricettaDTO.setExtras(ricetta.getExtras().stream().map(extra -> new ExtraQuantityDTO(
                extra.getExtra().getId(),
                extra.getQuantity(),
                extra.getUM()
        )).collect(Collectors.toList()));
        ricettaDTO.setGarnishes(ricetta.getGarnishes().stream().map(garnish -> new GarnishQuantityDTO(
                garnish.getGuarnizione().getId(),
                garnish.getQuantity(),
                garnish.getUM()
        )).collect(Collectors.toList()));
        return ricettaDTO;
    }
}
