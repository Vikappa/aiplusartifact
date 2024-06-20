package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.BrandTonica;
import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.entities.stockentities.Tonica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TonicaDAO extends JpaRepository<Tonica, Long> {
    long countByFlavour(Flavour flavour);
    Tonica findFirstByFlavour(Flavour flavour);

    Optional<Tonica> findByNameAndFlavourAndBrandTonicaAndGinTonicIsNull(
            String name,
            Flavour flavour,
            BrandTonica brandTonica
    );

    Optional<Tonica> findFirstByNameAndFlavourAndBrandTonicaAndGinTonicIsNull(
            String name,
            Flavour flavour,
            BrandTonica brandTonica
    );

    @Query("SELECT t.name AS name, t.brandTonica.name AS brandName, t.flavour.name AS flavourName, COUNT(t) AS count " +
            "FROM Tonica t " +
            "WHERE t.ginTonic IS NULL " +
            "GROUP BY t.name, t.brandTonica.name, t.flavour.name")
    List<Object[]> findTonicheNonAssociateAGinTonic();

    @Query("SELECT COUNT(t) " +
            "FROM Tonica t " +
            "WHERE t.flavour = :flavour " +
            "AND t.ginTonic IS NULL")
    long countByFlavourAndGinTonicIsNull(Flavour flavour);

    @Query("SELECT t.id, t.UM, t.name, t.flavour.name AS flavourName, t.scadenza_tonica, t.brandTonica.name AS brandName, t.carico.nCarico, t.carico.data " +
            "FROM Tonica t " +
            "WHERE t.ginTonic IS NULL")
    List<Object[]> findTonicheNonAssociateAGinTonicConCarico();
}
