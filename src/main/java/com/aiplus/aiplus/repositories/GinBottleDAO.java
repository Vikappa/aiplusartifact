package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.GinBottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GinBottleDAO extends JpaRepository<GinBottle, Long> {
}
