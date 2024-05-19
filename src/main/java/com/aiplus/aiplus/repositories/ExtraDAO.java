package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Extra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraDAO extends JpaRepository<Extra, Long> {
    Extra findByNameAndUMAndQtaExtraGreaterThanEqual(String name, String UM, int qtaExtra);
}
