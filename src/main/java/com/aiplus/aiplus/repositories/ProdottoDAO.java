package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.stockentities.Prodotto;
import com.aiplus.aiplus.payloads.DTO.InventorySummaryDTO;
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

    @Query("SELECT p.name as name, p.UM as um, p.ginFlavour.name as flavour, p.expirationDate as expirationDate, SUM(p.volume) as totalVolume, COUNT(p) as totalQuantity, p.brand.name as ginBrand, p.brand.imageUrl as ginImage " +
            "FROM GinBottle p GROUP BY p.name, p.UM, p.ginFlavour.name, p.expirationDate, p.brand.name, p.brand.imageUrl")
    List<GinBottleSummaryDTO> summarizeGinBottle();

    @Query("SELECT p.name as name, p.UM as um, p.brandTonica.name as brand, COUNT(p) as totalQuantity " +
            "FROM Tonica p GROUP BY p.name, p.UM, p.brandTonica.name")
    List<TonicaSummaryDTO> summarizeTonica();

    @Query("SELECT p.name as name, p.UM as um, p.flavour.name as flavour, SUM(p.qtaExtra) as totalQuantity " +
            "FROM Extra p GROUP BY p.name, p.UM, p.flavour.name")
    List<ExtraSummaryDTO> summarizeExtra();

    @Query("SELECT p.name as name, p.UM as um, p.flavour.name as flavour, p.colore.name as color, SUM(p.quantitaGarnish) as totalQuantity " +
            "FROM Guarnizione p GROUP BY p.name, p.UM, p.flavour.name, p.colore.name")
    List<GarnishSummaryDTO> summarizeGarnish();


    }
