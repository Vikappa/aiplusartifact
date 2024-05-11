package com.aiplus.aiplus.controllers;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.payloads.login.RegisterUserDTO;
import com.aiplus.aiplus.security.JWTTools;
import com.aiplus.aiplus.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.aiplus.aiplus.repositories.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    JWTTools jwtTools;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userRepository;

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody RegisterUserDTO body) {
        User user = new User();
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setRole(body.role());
        user.setPassword(passwordEncoder.encode(body.password()));

        if (userDAO.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        User savedUser = userService.addUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader(value="Authorization") String token) {
        try {

            System.out.println(token);

            User user = userService.getUserByToken(token);
            System.out.println(user.toString());

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Errore get profile from token");
        }
    }

}
