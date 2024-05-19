package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RicettaDAO extends JpaRepository<Ricetta, Long> {
}
