package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GinFlavourDAO extends JpaRepository<GinFlavour, Long> {

    @Override
    GinFlavour getReferenceById(Long aLong);

    List<GinFlavour> findAll();

    GinFlavour findByName(String name);


}
