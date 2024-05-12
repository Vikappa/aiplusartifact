package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import com.aiplus.aiplus.repositories.GinFlavourDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GinFlavourService {

    @Autowired
    private GinFlavourDAO ginFlavourDAO;
    public List<GinFlavour> getAllGinFlavours() {
        return ginFlavourDAO.findAll();
    }
}
