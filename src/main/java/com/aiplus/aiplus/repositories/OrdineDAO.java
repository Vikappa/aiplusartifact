package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.movimenti.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineDAO extends JpaRepository<Ordine, Long> {
    List<Ordine> findTop10ByOrderByDataOrdineDesc();
}
