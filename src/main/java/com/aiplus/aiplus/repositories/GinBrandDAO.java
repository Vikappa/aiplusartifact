package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.GinBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GinBrandDAO extends JpaRepository<GinBrand, Long> {
}
