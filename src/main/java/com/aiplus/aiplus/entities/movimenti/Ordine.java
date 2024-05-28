package com.aiplus.aiplus.entities.movimenti;

import com.aiplus.aiplus.entities.stockentities.GinTonic;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordini")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private GinTonic ginTonic;

    private int nTavolo;

    @Enumerated(EnumType.STRING)
    private ORDER_STATUS status;

    private LocalDateTime dataOrdine;

    private LocalDateTime dataPreparazione;

    @PrePersist
    protected void onCreate() {
        dataOrdine = LocalDateTime.now();
    }

    public void setPreparazione() {
        this.dataPreparazione = LocalDateTime.now();
    }

    public String getDataPreparazioneFormatted() {
        if (dataPreparazione != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return dataPreparazione.format(formatter);
        }
        return null;
    }
}
