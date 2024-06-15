package com.aiplus.aiplus.services;

import com.aiplus.aiplus.payloads.DTO.totalresumeDTOs.AllbottlesresumeDTO;
import com.aiplus.aiplus.payloads.records.*;
import com.aiplus.aiplus.repositories.ProdottoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MagazzinoService {

    @Autowired
    private ProdottoDAO prodottoDAO;
    @Autowired
    private GinBottleService ginBottleService;

    public List<GinBottleSummaryDTO> getGinBottleSummary() {
        List<GinBottleSummaryDTO> summaries = prodottoDAO.summarizeGinBottle();
        return summaries;
    }



    public List<TonicaSummaryDTO> getTonicaSummary() {
        return prodottoDAO.summarizeTonica();
    }

    public List<ExtraSummaryDTO> getExtraSummary() {
        List<ExtraSummaryDTO> extras = prodottoDAO.summarizeExtra();
        Map<String, Integer> usedExtraQuantities = prodottoDAO.findUsedExtraQuantities().stream()
                .collect(Collectors.toMap(
                        row -> row[0] + "|" + row[1] + "|" + row[2], // Concatenazione dei campi per creare una chiave unica
                        row -> ((Number) row[3]).intValue()
                ));

        return extras.stream().map(extra -> {
            String key = extra.getName() + "|" + extra.getUm() + "|" + extra.getFlavour();
            Long totalQuantity = extra.getTotalQuantity() - usedExtraQuantities.getOrDefault(key, 0);
            return new ExtraSummaryDTO() {
                @Override
                public String getName() {
                    return extra.getName();
                }

                @Override
                public String getUm() {
                    return extra.getUm();
                }

                @Override
                public String getFlavour() {
                    return extra.getFlavour();
                }

                @Override
                public Long getTotalQuantity() {
                    return totalQuantity;
                }
            };
        }).collect(Collectors.toList());
    }

    public List<GarnishSummaryDTO> getGarnishSummary() {
        List<GarnishSummaryDTO> garnishes = prodottoDAO.summarizeGarnish();
        Map<String, Integer> usedGarnishQuantities = prodottoDAO.findUsedGarnishQuantities().stream()
                .collect(Collectors.toMap(
                        row -> row[0] + "|" + row[1] + "|" + row[2] + "|" + row[3], // Concatenazione dei campi per creare una chiave unica
                        row -> ((Number) row[4]).intValue()
                ));

        return garnishes.stream().map(garnish -> {
            String key = garnish.getName() + "|" + garnish.getUm() + "|" + garnish.getFlavour() + "|" + garnish.getColor();
            Long totalQuantity = garnish.getTotalQuantity() - usedGarnishQuantities.getOrDefault(key, 0);
            return new GarnishSummaryDTO() {
                @Override
                public String getName() {
                    return garnish.getName();
                }

                @Override
                public String getUm() {
                    return garnish.getUm();
                }

                @Override
                public String getFlavour() {
                    return garnish.getFlavour();
                }

                @Override
                public String getColor() {
                    return garnish.getColor();
                }

                @Override
                public Long getTotalQuantity() {
                    return totalQuantity;
                }
            };
        }).collect(Collectors.toList());
    }

    public ResponseEntity<List<Object>> getResume() {
        List<Object> summary = new ArrayList<>();
        summary.addAll(getGinBottleSummary());
        summary.addAll(getTonicaSummary());
        summary.addAll(getExtraSummary());
        summary.addAll(getGarnishSummary());
        return ResponseEntity.ok().body(summary);
    }

    public List<AllbottlesresumeDTO> getTotalResume(){

        return ginBottleService.getTotalResume();
    }
}
