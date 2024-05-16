package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExtraDAO extends JpaRepository<Extra, Long> {

    @Query("SELECT DISTINCT e.name FROM Extra e")
    List<String> getInStoreList();
}
