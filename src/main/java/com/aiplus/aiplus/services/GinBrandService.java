package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.GinBrand;
import com.aiplus.aiplus.payloads.DTO.GinBrandDTO;
import com.aiplus.aiplus.repositories.GinBrandDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GinBrandService {

    @Autowired
    private GinBrandDAO ginBrandDAO;

    public void createGinBrand(GinBrandDTO ginBrandDTO) {
        GinBrand ginBrand = new GinBrand();
        ginBrand.setName(ginBrandDTO.getName());
        ginBrand.setImageUrl(ginBrandDTO.getImageUrl());
        ginBrand.setDescription(ginBrandDTO.getDescription());
        ginBrand.setSovrapprezzo(ginBrandDTO.getSovrapprezzo());
        ginBrandDAO.save(ginBrand);
    }

    public List<GinBrand> findAll() {
        return ginBrandDAO.findAll();
    }

    public GinBrand findByName(String brandName) {
        return ginBrandDAO.findByName(brandName);
    }

    public void deleteBrandByName(String brandName) {
        GinBrand brand = ginBrandDAO.findByName(brandName);
        ginBrandDAO.delete(brand);
    }
}
