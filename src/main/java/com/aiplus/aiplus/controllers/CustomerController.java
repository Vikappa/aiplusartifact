package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.users.USER_ROLE;
import com.aiplus.aiplus.payloads.login.CustomerLoginDTO;
import com.aiplus.aiplus.payloads.login.UserLoginResponseDTO;
import com.aiplus.aiplus.security.JWTTools;
import com.aiplus.aiplus.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getntavolo")
    public ResponseEntity<Integer> getNTavolo(@RequestHeader(value="Authorization") String token){
        return ResponseEntity.ok().body(jwtTools.extractTableNumber(token));
    }

    @PostMapping("/createcustomer")
    public UserLoginResponseDTO logCustomer(@RequestBody @Validated CustomerLoginDTO customerLoginDTO){
        return new UserLoginResponseDTO(this.customerService.createCustomerAndGenerateToken(customerLoginDTO), USER_ROLE.CUSTOMER);
    }

}
