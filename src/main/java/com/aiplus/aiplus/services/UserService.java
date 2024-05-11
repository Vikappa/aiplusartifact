package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.repositories.UserDAO;
import com.aiplus.aiplus.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    JWTTools jwtTools;

    @Autowired
    UserDAO userDAO;

    public User addUser(User user) {
        return userDAO.save(user);
    }

    public User getUserByToken(String token) {

        UUID userID = jwtTools.extractUserUUID(token);
        userDAO.findById(userID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return userDAO.findByUUID(userID);

    }

}
