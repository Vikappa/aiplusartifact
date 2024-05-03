package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.GinBottle;
import com.aiplus.aiplus.entities.GinBrand;
import com.aiplus.aiplus.repositories.GinBottleDAO;
import com.aiplus.aiplus.repositories.GinBrandDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class GinBottleService {

    @Autowired
    private GinBottleDAO ginBottleDAO;

    @Autowired
    private GinBrandDAO ginBrandDAO;  // Assumo che esista un DAO per GinBrand

    @Transactional
    public void addGinBottleWithImage(Long brandId, LocalDate productionDate, String imageUrl) {
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
}
