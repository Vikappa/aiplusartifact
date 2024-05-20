package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.movimenti.IngredienteRicetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRicettaDAO extends JpaRepository<IngredienteRicetta, String> {
}
