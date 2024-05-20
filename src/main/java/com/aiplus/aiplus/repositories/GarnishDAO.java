package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Guarnizione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarnishDAO extends JpaRepository<Guarnizione, Long> {
    Guarnizione findByNameAndUMAndQuantitaGarnishGreaterThanEqual(String name, String UM, int quantitaGarnish);

    Optional<Guarnizione> findByNameAndUM(String name, String UM);
}
