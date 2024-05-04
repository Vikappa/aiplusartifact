package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.GinBrand;
import com.aiplus.aiplus.payloads.DTO.GinBrandDTO;
import com.aiplus.aiplus.repositories.GinBrandDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GinBrandService {

    @Autowired
    private GinBrandDAO ginBrandDAO;

    public GinBrand createOrUpdateGinBrand(GinBrand ginBrand) {
        return ginBrandDAO.save(ginBrand);
    }

    public void addGinBottleWithImage(String brandId, LocalDate productionDate, String imageUrl) {
        GinBrand brand = ginBrandDAO.findById(brandId).orElse(null);
    }

    public List<String> findAllBrandNames() {
        return ginBrandDAO.findAllBrandNames();
    }

    public List<GinBrand> findAll() {
        return ginBrandDAO.findAll();
    }

    public void createGinBrand(GinBrandDTO ginBrandDTO) {
        GinBrand ginBrand = new GinBrand();
        ginBrand.setName(ginBrandDTO.getName());
        ginBrand.setImageUrl(ginBrandDTO.getImageUrl());
        ginBrand.setDescription(ginBrandDTO.getDescription());
        ginBrand.setSovrapprezzo(ginBrandDTO.getSovrapprezzo());
        ginBrandDAO.save(ginBrand);
    }

    public GinBrand findByName(String brandName) {
        return ginBrandDAO.findByName(brandName);
    }
}

