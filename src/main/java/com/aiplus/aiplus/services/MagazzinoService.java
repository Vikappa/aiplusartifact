package com.aiplus.aiplus.services;

import com.aiplus.aiplus.payloads.DTO.InventorySummaryDTO;
import com.aiplus.aiplus.payloads.records.ExtraSummaryDTO;
import com.aiplus.aiplus.payloads.records.GarnishSummaryDTO;
import com.aiplus.aiplus.payloads.records.GinBottleSummaryDTO;
import com.aiplus.aiplus.payloads.records.TonicaSummaryDTO;
import com.aiplus.aiplus.repositories.ProdottoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MagazzinoService {

    @Autowired
    private ProdottoDAO prodottoDAO;


    public List<GinBottleSummaryDTO> getGinBottleSummary() {
        return prodottoDAO.summarizeGinBottle();
    }

    public List<TonicaSummaryDTO> getTonicaSummary() {
        return prodottoDAO.summarizeTonica();
    }

    public List<ExtraSummaryDTO> getExtraSummary() {
        return prodottoDAO.summarizeExtra();
    }

    public List<GarnishSummaryDTO> getGarnishSummary() {
        return prodottoDAO.summarizeGarnish();
    }

    public ResponseEntity<List<Object>> getResume() {
        List<Object> summary = new ArrayList<>();
        summary.addAll(getGinBottleSummary());
        summary.addAll(getTonicaSummary());
        summary.addAll(getExtraSummary());
        summary.addAll(getGarnishSummary());
        return ResponseEntity.ok().body(summary);
    }


}
