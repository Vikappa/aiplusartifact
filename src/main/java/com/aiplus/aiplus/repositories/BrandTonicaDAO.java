package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.BrandTonica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandTonicaDAO extends JpaRepository<BrandTonica, String> {
}
