package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.movimenti.Carico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaricoDAO extends JpaRepository<Carico, Long> {
}
