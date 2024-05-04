package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.GinBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GinBrandDAO extends JpaRepository<GinBrand, String> {

    @Query("SELECT g.name FROM GinBrand g")
    List<String> findAllBrandNames();

    @Query("SELECT g FROM GinBrand g")
    List<GinBrand> findAll();

    @Query("SELECT g FROM GinBrand g WHERE g.name = :brandName")
    GinBrand findByName(String brandName);
}
