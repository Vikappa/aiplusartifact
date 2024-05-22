package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.BrandTonica;
import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.entities.stockentities.Tonica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TonicaDAO extends JpaRepository<Tonica, Long> {
    long countByFlavour(Flavour flavour);
    Tonica findFirstByFlavour(Flavour flavour);

    Optional<Tonica> findByNameAndFlavourAndBrandTonicaAndGinTonicIsNull(
            String name,
            Flavour flavour,
            BrandTonica brandTonica
    );

    Optional<Tonica> findFirstByNameAndFlavourAndBrandTonicaAndGinTonicIsNull(
            String name,
            Flavour flavour,
            BrandTonica brandTonica
    );
}
