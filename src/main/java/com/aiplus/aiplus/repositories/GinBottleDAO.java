package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.GinBottle;
import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GinBottleDAO extends JpaRepository<GinBottle, Long> {
    GinBottle findFirstByGinFlavourAndCurrentVolumeGreaterThanEqual(GinFlavour ginFlavour, double currentVolume);

    @Query("SELECT SUM(g.currentVolume) FROM GinBottle g WHERE g.ginFlavour = :ginFlavour")
    Double sumCurrentVolumeByGinFlavour(GinFlavour ginFlavour);

    @Query("SELECT g.brand.name AS brandName, g.ginFlavour.name AS flavourName, g.name AS ginName, SUM(g.currentVolume) AS totalVolume, COUNT(g) AS count, g.brand.sovrapprezzo AS sovrapprezzo " +
            "FROM GinBottle g " +
            "GROUP BY g.brand.name, g.ginFlavour.name, g.name, g.brand.sovrapprezzo")
    List<Object[]> findGinBottlesGroupedByBrandAndFlavourAndName();

    List<GinBottle> findByNameAndGinFlavour_NameAndBrand_NameAndCurrentVolumeGreaterThanEqual(String name, String ginFlavourName, String brandName, double currentVolume);
    @Query("SELECT g.id, g.UM, g.name, g.alcoholPercentage, g.currentVolume, g.expirationDate, g.productionDate, g.volume, g.carico.nCarico, g.carico.data, g.brand.name, g.ginFlavour.name FROM GinBottle g WHERE g.currentVolume > 0")
    List<Object[]> getTotalResume();

}
