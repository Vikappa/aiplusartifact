package com.aiplus.aiplus.payloads.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RicettaDTO {
    private long id;
    private String name;
    private String ginFlavourName;
    private String tonicaName;
    private List<ExtraQuantityDTO> extras;
    private List<GarnishQuantityDTO> garnishes;
    private boolean preparabile;
    private int quantitaPreparabile;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGinFlavourName() {
        return ginFlavourName;
    }

    public void setGinFlavourName(String ginFlavourName) {
        this.ginFlavourName = ginFlavourName;
    }

    public String getTonicaName() {
        return tonicaName;
    }

    public void setTonicaName(String tonicaName) {
        this.tonicaName = tonicaName;
    }

    public List<ExtraQuantityDTO> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtraQuantityDTO> extras) {
        this.extras = extras;
    }

    public List<GarnishQuantityDTO> getGarnishes() {
        return garnishes;
    }

    public void setGarnishes(List<GarnishQuantityDTO> garnishes) {
        this.garnishes = garnishes;
    }

    public boolean isPreparabile() {
        return preparabile;
    }

    public void setPreparabile(boolean preparabile) {
        this.preparabile = preparabile;
    }

    public int getQuantitaPreparabile() {
        return quantitaPreparabile;
    }

    public void setQuantitaPreparabile(int quantitaPreparabile) {
        this.quantitaPreparabile = quantitaPreparabile;
    }
}
