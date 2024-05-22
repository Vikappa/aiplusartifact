package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.ExtraQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraQuantityDAO extends JpaRepository<ExtraQuantity, Long> {
}
