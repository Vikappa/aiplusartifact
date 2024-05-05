package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.GinBottle;
import com.aiplus.aiplus.entities.GinBrand;
import com.aiplus.aiplus.payloads.DTO.GinBottleDTO;
import com.aiplus.aiplus.repositories.GinBottleDAO;
import com.aiplus.aiplus.repositories.GinBrandDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hibernate.query.sqm.tree.SqmNode.log;

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

        GinBottle bottle = new GinBottle();
        bottle.setBrand(brand);
        bottle.setProductionDate(ginBottleDto.getProductionDate());
        bottle.setImageUrl(ginBottleDto.getImageUrl());
        bottle.setVolume(ginBottleDto.getVolume());  // Assumendo che questi campi siano aggiunti al DTO
        bottle.setAlcoholPercentage(ginBottleDto.getAlcoholPercentage());
        bottle.setBatchNumber(ginBottleDto.getBatchNumber());
        bottle.setFlavour(ginBottleDto.getFlavour());

        ginBottleDAO.save(bottle);
        log.info("Gin bottle saved successfully with id: {}", bottle.getId());
    }
}

