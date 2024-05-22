package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.GinBottle;
import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GinBottleDAO extends JpaRepository<GinBottle, Long> {
    GinBottle findFirstByGinFlavourAndCurrentVolumeGreaterThanEqual(GinFlavour ginFlavour, double currentVolume);

    Optional<GinBottle> findByNameAndGinFlavour_NameAndBrand_NameAndCurrentVolumeGreaterThanEqual(
            String name,
            String ginFlavourName,
            String brandName,
            double currentVolume
    );
}
