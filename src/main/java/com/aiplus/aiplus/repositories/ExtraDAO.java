package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Extra;
import com.aiplus.aiplus.payloads.records.ExtraAvailabilityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExtraDAO extends JpaRepository<Extra, Long> {
    List<Extra> findByNameAndUMAndQtaExtraGreaterThanEqual(String name, String UM, int qtaExtra);

    Optional<Extra> findByName(String name);

    @Query("SELECT e FROM Extra e WHERE e.name = :name AND e.UM = :um")
    List<Extra> findByNameAndUM(@Param("name") String name, @Param("um") String um);

    Extra findTop1ByNameAndUMAndQtaExtraGreaterThanEqual(String name, String UM, int qtaExtra);

    @Query("SELECT new com.aiplus.aiplus.payloads.records.ExtraAvailabilityDTO(e, (e.qtaExtra - COALESCE((SELECT SUM(eq.quantity) FROM ExtraQuantity eq WHERE eq.extra.id = e.id), 0))) " +
            "FROM Extra e " +
            "WHERE e.name = :name AND e.UM = :um AND (e.qtaExtra - COALESCE((SELECT SUM(eq.quantity) FROM ExtraQuantity eq WHERE e.id = e.id), 0)) >= :requiredQuantity")
    List<ExtraAvailabilityDTO> findAvailableExtras(@Param("name") String name, @Param("um") String um, @Param("requiredQuantity") int requiredQuantity);

    @Query("SELECT e FROM Extra e WHERE e.name = :name AND e.UM = :um AND e.qtaExtra > 0 ORDER BY e.id ASC")
    Optional<Extra> findFirstByNameAndUMAndQtaExtraGreaterThanZero(@Param("name") String name, @Param("um") String um);
}
