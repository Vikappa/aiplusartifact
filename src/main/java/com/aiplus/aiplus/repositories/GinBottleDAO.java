package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.GinBottle;
import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GinBottleDAO extends JpaRepository<GinBottle, Long> {
    GinBottle findFirstByGinFlavourAndCurrentVolumeGreaterThanEqual(GinFlavour ginFlavour, double currentVolume);

}
