package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User addUser(User user) {
        return userDAO.save(user);
    }
}
