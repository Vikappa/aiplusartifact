package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Extra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtraDAO extends JpaRepository<Extra, Long> {
    Extra findByNameAndUMAndQtaExtraGreaterThanEqual(String name, String UM, int qtaExtra);

    Optional<Extra> findByName(String name);

    Optional<Extra> findByNameAndUM(String name,String UM);
}
