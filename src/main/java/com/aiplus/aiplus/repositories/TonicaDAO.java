package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.entities.stockentities.Tonica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TonicaDAO extends JpaRepository<Tonica, Long> {
    long countByFlavour(Flavour flavour);
    Tonica findFirstByFlavour(Flavour flavour);

}
