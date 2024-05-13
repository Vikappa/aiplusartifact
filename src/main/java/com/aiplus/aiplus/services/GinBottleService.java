package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.GinBottle;
import com.aiplus.aiplus.entities.stockentities.GinBrand;
import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import com.aiplus.aiplus.payloads.DTO.GinBottleDTO;
import com.aiplus.aiplus.repositories.GinBottleDAO;
import com.aiplus.aiplus.repositories.GinBrandDAO;
import com.aiplus.aiplus.repositories.GinFlavourDAO;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class GinBottleService {
    private static final Logger log = LoggerFactory.getLogger(GinBottleService.class);


    @Autowired
    private GinBottleDAO ginBottleDAO;

    @Autowired
    private GinBrandDAO ginBrandDAO;

    @Autowired
    private GinFlavourDAO ginFlavourDAO;

    @Transactional
    public void addGinBottle(GinBottleDTO ginBottleDto) {
        Logger log = LoggerFactory.getLogger(this.getClass());

        if (ginBottleDto.getBrandId() == null) {
            log.error("Null brand ID provided");
            throw new IllegalArgumentException("Brand ID must not be null");
        }

        GinBrand brand = ginBrandDAO.findById(ginBottleDto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found for ID: " + ginBottleDto.getBrandId()));



        GinFlavour flavour = ginFlavourDAO.findByName(ginBottleDto.getGinFlavourString());
        if(flavour == null){
            throw new EntityNotFoundException("Flavour not found for name: " + ginBottleDto.getGinFlavourString());
        }

        LocalDate expirationDate = ginBottleDto.getProductionDate().plusYears(2);

        GinBottle bottle = new GinBottle();
        bottle.setBrand(brand);
        bottle.setGinFlavour(flavour);
        bottle.setProductionDate(ginBottleDto.getProductionDate());
        bottle.setImageUrl(ginBottleDto.getImageUrl());
        bottle.setVolume(ginBottleDto.getVolume());
        bottle.setAlcoholPercentage(ginBottleDto.getAlcoholPercentage());
        bottle.setBatchNumber(String.valueOf(ginBottleDto.getBatchNumber()));
        bottle.setCurrentVolume(ginBottleDto.getVolume());
        bottle.setExpirationDate(expirationDate);
        bottle.setName(ginBottleDto.getName());
        bottle.setUM(ginBottleDto.getUM());
        bottle.setGinFlavour(flavour);

        ginBottleDAO.save(bottle);
        log.info("Gin bottle saved successfully with id: {}", bottle.getId());
    }



    public GinBottle getGinBottle(long id) {
        return ginBottleDAO.findById(id).orElse(null);
    }

    public Iterable<GinBottle> getAllGinBottles() {
        return ginBottleDAO.findAll();
    }

    public void deleteGinBottle(long id) {
        ginBottleDAO.deleteById(id);
    }
}

