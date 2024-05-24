package com.aiplus.aiplus.services;

import com.aiplus.aiplus.payloads.DTO.GinBottleLineShort;
import com.aiplus.aiplus.payloads.DTO.TonicaBottleLineShort;
import com.aiplus.aiplus.repositories.TonicaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TonicaService {

    @Autowired
    private TonicaDAO tonicaDAO;

    public List<TonicaBottleLineShort> getTonicaLineShort() {
        List<Object[]> queryResponse = tonicaDAO.findTonicheNonAssociateAGinTonic();
        return queryResponse.stream()
                .map(queryResponseLine -> new TonicaBottleLineShort(
                        (String) queryResponseLine[0],  // name
                        (String) queryResponseLine[2],  // brandName
                        (String) queryResponseLine[1],  // flavourName
                        (Long) queryResponseLine[3]     // count
                ))
                .collect(Collectors.toList());
    }
}
