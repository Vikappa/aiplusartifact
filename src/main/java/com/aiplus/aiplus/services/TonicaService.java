package com.aiplus.aiplus.services;

import com.aiplus.aiplus.payloads.DTO.TonicaBottleLineShort;
import com.aiplus.aiplus.payloads.DTO.totalresumeDTOs.AlltonicheresumeDTO;
import com.aiplus.aiplus.repositories.TonicaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;import java.time.LocalDate;


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

    public List<AlltonicheresumeDTO> getTonicheNonAssociateAGinTonic() {
        List<Object[]> results = tonicaDAO.findTonicheNonAssociateAGinTonicConCarico();
        return results.stream()
                .map(result -> new AlltonicheresumeDTO(
                        (Long) result[0],
                        (String) result[1],
                        (String) result[2],
                        (String) result[3],
                        (LocalDate) result[4],
                        (String) result[5],
                        (Long) result[6],
                        (String) result[7]
                ))
                .collect(Collectors.toList());
    }
}
