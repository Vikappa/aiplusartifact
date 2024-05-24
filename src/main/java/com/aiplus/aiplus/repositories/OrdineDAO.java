package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.movimenti.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineDAO extends JpaRepository<Ordine, Long> {
}
