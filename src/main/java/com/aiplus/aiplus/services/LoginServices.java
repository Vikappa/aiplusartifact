package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.exceptions.UserNotFoundException;
import com.aiplus.aiplus.exceptions.UnauthorizedException;
import com.aiplus.aiplus.payloads.login.UserLoginDTO;
import com.aiplus.aiplus.repositories.UserDAO;
import com.aiplus.aiplus.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {

    @Autowired
    UserDAO userDAO;

    @Autowired
    JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(UserLoginDTO userLoginDTO){

        try {
        User user = userDAO.findByEmail(userLoginDTO.email());

        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + userLoginDTO.email());
        }


        if (!user.getPassword().equals(userLoginDTO.password())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return jwtTools.createToken(user);
        } catch (UserNotFoundException e) {
            return e.getMessage();
        }
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

}
