package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.GinBottle;
import com.aiplus.aiplus.entities.stockentities.GinBrand;
import com.aiplus.aiplus.payloads.DTO.GinBottleDTO;
import com.aiplus.aiplus.repositories.GinBottleDAO;
import com.aiplus.aiplus.repositories.GinBrandDAO;
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

    @Transactional
    public void addGinBottleWithImage(String brandId, LocalDate productionDate, String imageUrl) {
        GinBrand brand = ginBrandDAO.findById(brandId).orElse(null);
        if (brand != null) {
            GinBottle bottle = new GinBottle();
            bottle.setBrand(brand);
            bottle.setProductionDate(productionDate);
            bottle.setImageUrl(imageUrl);
            ginBottleDAO.save(bottle);
        } else {
            System.out.println("Marchio di gin non trovato.");
        }
    }

    @Transactional
    public void addGinBottle(GinBottleDTO ginBottleDto) {
        GinBrand brand = ginBrandDAO.findById(ginBottleDto.getBrandId()).orElse(null);
        if (brand == null) {
            log.error("Brand not found for id: {}", ginBottleDto.getBrandId());
            return;
        }

        LocalDate expirationDate = ginBottleDto.getProductionDate().plusYears(2);

        GinBottle bottle = new GinBottle();
        bottle.setBrand(brand);
        bottle.setProductionDate(ginBottleDto.getProductionDate());
        bottle.setImageUrl(ginBottleDto.getImageUrl());
        bottle.setVolume(ginBottleDto.getVolume());
        bottle.setAlcoholPercentage(ginBottleDto.getAlcoholPercentage());
        bottle.setBatchNumber(ginBottleDto.getBatchNumber());
        bottle.setCurrentVolume(ginBottleDto.getVolume());
        bottle.setExpirationDate(expirationDate);
        bottle.setName(ginBottleDto.getName());
        bottle.setUM(ginBottleDto.getUM());
        bottle.setFlavour(ginBottleDto.getFlavour());

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

