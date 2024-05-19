package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Guarnizione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarnishDAO extends JpaRepository<Guarnizione, Long> {
    Guarnizione findByNameAndUMAndQuantitaGarnishGreaterThanEqual(String name, String UM, int quantitaGarnish);
}
