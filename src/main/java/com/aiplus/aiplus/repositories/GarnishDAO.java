package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Guarnizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarnishDAO extends JpaRepository<Guarnizione, Long> {
}
