package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Flavour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlavourDAO extends JpaRepository<Flavour, Long> {
}
