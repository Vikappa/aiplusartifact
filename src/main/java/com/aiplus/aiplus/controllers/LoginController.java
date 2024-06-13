package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.users.USER_ROLE;
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

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        String token = this.loginService.authenticateUserAndGenerateToken(userLoginDTO);
        USER_ROLE role = this.loginService.getRole(userLoginDTO);
        return new UserLoginResponseDTO(token, role);
    }
}
