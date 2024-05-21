package com.aiplus.aiplus.services;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.repositories.UserDAO;
import com.aiplus.aiplus.security.JWTTools;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    JWTTools jwtTools;

    @Autowired
    UserDAO userDAO;

    public User addUser(User user) {
        return userDAO.save(user);
    }


    public User findById(UUID userId) throws ChangeSetPersister.NotFoundException {
        return this.userDAO.findById(userId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public String extractIdFromToken(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token).getPayload().getSubject(); // Il subject è l'id dell'utente
    }

    public Optional<User> getUserByToken(String token) {
        String id = extractIdFromToken(token);
        return userDAO.findById(UUID.fromString(id));
    }

}
