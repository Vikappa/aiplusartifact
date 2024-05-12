package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.ColoreGuarnizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColoreGuarnizioneDAO extends JpaRepository<ColoreGuarnizione, Long> {
}
