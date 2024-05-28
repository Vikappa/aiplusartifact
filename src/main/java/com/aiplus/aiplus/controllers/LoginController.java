package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.payloads.login.UserLoginDTO;
import com.aiplus.aiplus.payloads.login.UserLoginResponseDTO;
import com.aiplus.aiplus.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServices loginService;

    @PostMapping
    public UserLoginResponseDTO login(@RequestBody @Validated UserLoginDTO userLoginDTO){
        return new UserLoginResponseDTO(this.loginService.authenticateUserAndGenerateToken(userLoginDTO), this.loginService.getRole(userLoginDTO));
    }

}
