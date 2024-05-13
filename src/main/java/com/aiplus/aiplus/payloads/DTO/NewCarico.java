package com.aiplus.aiplus.payloads.DTO;

import com.aiplus.aiplus.entities.stockentities.Prodotto;
import com.aiplus.aiplus.entities.users.User;

import java.util.ArrayList;

public record NewCarico(
        User operatore,
        ArrayList<NewProdotto> prodotti,
        String note
) {}
