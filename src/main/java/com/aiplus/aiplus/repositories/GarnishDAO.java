package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Guarnizione;
import com.aiplus.aiplus.payloads.DTO.totalresumeDTOs.AllgarnishresumeDTO;
import com.aiplus.aiplus.payloads.records.GarnishAvailabilityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GarnishDAO extends JpaRepository<Guarnizione, Long> {
    List<Guarnizione> findByNameAndUMAndQuantitaGarnishGreaterThanEqual(String name, String UM, int quantitaGarnish);

    @Query("SELECT g FROM Guarnizione g WHERE g.name = :name AND g.UM = :um")
    Optional<Guarnizione> findByNameAndUM(@Param("name") String name, @Param("um") String um);

    @Query("SELECT g FROM Guarnizione g WHERE g.name = :name AND g.UM = :um")
    List<Guarnizione> findAllByNameAndUM(@Param("name") String name, @Param("um") String um);

    Guarnizione findTop1ByNameAndUMAndQuantitaGarnishGreaterThanEqual(String name, String UM, int quantitaGarnish);

    @Query("SELECT new com.aiplus.aiplus.payloads.records.GarnishAvailabilityDTO(g, (g.quantitaGarnish - COALESCE((SELECT SUM(gq.quantity) FROM GarnishQuantity gq WHERE gq.guarnizione.id = g.id), 0))) " +
            "FROM Guarnizione g " +
            "WHERE g.name = :name AND g.UM = :um AND (g.quantitaGarnish - COALESCE((SELECT SUM(gq.quantity) FROM GarnishQuantity gq WHERE gq.guarnizione.id = g.id), 0)) >= :requiredQuantity")
    List<GarnishAvailabilityDTO> findAvailableGarnishes(@Param("name") String name, @Param("um") String um, @Param("requiredQuantity") int requiredQuantity);

    @Query("SELECT g FROM Guarnizione g WHERE g.name = :name AND g.UM = :um AND g.quantitaGarnish > 0 ORDER BY g.id ASC")
    Optional<Guarnizione> findFirstByNameAndUMAndQuantitaGarnishGreaterThanZero(@Param("name") String name, @Param("um") String um);


    @Query("SELECT new com.aiplus.aiplus.payloads.DTO.totalresumeDTOs.AllgarnishresumeDTO(" +
            "g.name, g.UM, g.carico.nCarico, g.flavour.name, g.colore.name, " +
            "(g.quantitaGarnish - COALESCE(SUM(gq.quantity), 0))) " +
            "FROM Guarnizione g " +
            "LEFT JOIN g.garnishQuantities gq " +
            "GROUP BY g.id, g.name, g.UM, g.carico.nCarico, g.flavour.name, g.colore.name, g.quantitaGarnish")
    List<AllgarnishresumeDTO> totalResume();
}
