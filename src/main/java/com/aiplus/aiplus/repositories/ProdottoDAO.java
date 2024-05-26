package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Prodotto;
import com.aiplus.aiplus.payloads.records.ExtraSummaryDTO;
import com.aiplus.aiplus.payloads.records.GarnishSummaryDTO;
import com.aiplus.aiplus.payloads.records.GinBottleSummaryDTO;
import com.aiplus.aiplus.payloads.records.TonicaSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoDAO extends JpaRepository<Prodotto, Long> {

    @Query("SELECT p.name as name, p.UM as um, p.ginFlavour.name as flavour, p.expirationDate as expirationDate, " +
            "SUM(p.currentVolume) as totalVolume, COUNT(p) as totalQuantity, p.brand.name as ginBrand, p.brand.imageUrl as ginImage " +
            "FROM GinBottle p " +
            "WHERE p.currentVolume > 0 " +
            "GROUP BY p.name, p.UM, p.ginFlavour.name, p.expirationDate, p.brand.name, p.brand.imageUrl")
    List<GinBottleSummaryDTO> summarizeGinBottle();

    @Query("SELECT p.name as name, p.UM as um, p.brandTonica.name as brand, p.flavour.name as flavour, " +
            "COUNT(p) as totalQuantity " +
            "FROM Tonica p " +
            "LEFT JOIN p.brandTonica " +
            "LEFT JOIN p.flavour " +
            "WHERE p.ginTonic IS NULL " +
            "GROUP BY p.name, p.UM, p.brandTonica.name, p.flavour.name")
    List<TonicaSummaryDTO> summarizeTonica();

    @Query("SELECT p.name as name, p.UM as um, p.flavour.name as flavour, SUM(p.qtaExtra) as totalQuantity " +
            "FROM Extra p " +
            "GROUP BY p.name, p.UM, p.flavour.name")
    List<ExtraSummaryDTO> summarizeExtra();

    @Query("SELECT p.name as name, p.UM as um, p.flavour.name as flavour, p.colore.name as color, SUM(p.quantitaGarnish) as totalQuantity " +
            "FROM Guarnizione p " +
            "GROUP BY p.name, p.UM, p.flavour.name, p.colore.name")
    List<GarnishSummaryDTO> summarizeGarnish();

    @Query("SELECT eq.extra.name as name, eq.extra.UM as um, eq.extra.flavour.name as flavour, SUM(eq.quantity) as usedQuantity " +
            "FROM ExtraQuantity eq " +
            "WHERE eq.ginTonic IS NOT NULL " +
            "GROUP BY eq.extra.name, eq.extra.UM, eq.extra.flavour.name")
    List<Object[]> findUsedExtraQuantities();

    @Query("SELECT gq.guarnizione.name as name, gq.guarnizione.UM as um, gq.guarnizione.flavour.name as flavour, gq.guarnizione.colore.name as color, SUM(gq.quantity) as usedQuantity " +
            "FROM GarnishQuantity gq " +
            "WHERE gq.ginTonic IS NOT NULL " +
            "GROUP BY gq.guarnizione.name, gq.guarnizione.UM, gq.guarnizione.flavour.name, gq.guarnizione.colore.name")
    List<Object[]> findUsedGarnishQuantities();
}
