package com.aiplus.aiplus.entities.movimenti;

import com.aiplus.aiplus.entities.stockentities.Prodotto;
import com.aiplus.aiplus.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nCarico;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private User operatore;

    private String data;

    @OneToMany(mappedBy = "carico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prodotto> prodotti;

    private String note;
}
