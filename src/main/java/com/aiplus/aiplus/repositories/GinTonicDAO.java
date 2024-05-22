package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.GinTonic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GinTonicDAO extends JpaRepository<GinTonic, Long> {
}
