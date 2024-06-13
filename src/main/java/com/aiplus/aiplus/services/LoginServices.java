package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.exceptions.UserNotFoundException;
import com.aiplus.aiplus.exceptions.UnauthorizedException;
import com.aiplus.aiplus.payloads.login.UserLoginDTO;
import com.aiplus.aiplus.repositories.UserDAO;
import com.aiplus.aiplus.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import com.aiplus.aiplus.entities.users.USER_ROLE;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {

    @Autowired
    UserDAO userDAO;

    @Autowired
    JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(UserLoginDTO userLoginDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userDAO.findByEmail(userLoginDTO.email());

        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + userLoginDTO.email());
        }

        if (!passwordEncoder.matches(userLoginDTO.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return jwtTools.createToken(user);
    }

    public USER_ROLE getRole(UserLoginDTO userLoginDTO) {
        User user = userDAO.findByEmail(userLoginDTO.email());

        if (user == null) {
            throw new UnauthorizedException("Invalid credentials, cannot find role");
        }

        return user.getRole();
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}

