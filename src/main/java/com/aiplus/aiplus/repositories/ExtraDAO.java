package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Extra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtraDAO extends JpaRepository<Extra, Long> {
    List<Extra> findByNameAndUMAndQtaExtraGreaterThanEqual(String name, String UM, int qtaExtra);

    Optional<Extra> findByName(String name);

    Optional<Extra> findByNameAndUM(String name,String UM);

    Extra findTop1ByNameAndUMAndQtaExtraGreaterThanEqual(String name, String UM, int qtaExtra);


}
