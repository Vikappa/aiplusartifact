package com.aiplus.aiplus.services;

import com.aiplus.aiplus.payloads.login.CustomerLoginDTO;
import com.aiplus.aiplus.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private JWTTools jwtTools;

    public String createCustomerAndGenerateToken(CustomerLoginDTO customerLoginDTO) {
        return jwtTools.createCustomerToken(customerLoginDTO);
    }
}
