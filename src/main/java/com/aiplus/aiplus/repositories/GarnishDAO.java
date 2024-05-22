package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Guarnizione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GarnishDAO extends JpaRepository<Guarnizione, Long> {
    List<Guarnizione> findByNameAndUMAndQuantitaGarnishGreaterThanEqual(String name, String UM, int quantitaGarnish);

    Optional<Guarnizione> findByNameAndUM(String name, String UM);

    Guarnizione findTop1ByNameAndUMAndQuantitaGarnishGreaterThanEqual(String name, String UM, int quantitaGarnish);

}
