package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.GarnishQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarnishQuantityDAO extends JpaRepository<GarnishQuantity, Long> {
}
