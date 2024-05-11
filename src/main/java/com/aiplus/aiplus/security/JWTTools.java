package com.aiplus.aiplus.security;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60)) // Un ora di vita
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))) //Firma con algoritmo HMAC
                .compact();
    }

    public String extractUsername(String token) {

        return "";
    }

    public void verifyToken(String token){
        try {
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))).build().parse(token);
        } catch (Exception e){
            throw new UnauthorizedException("Invalid token");
        }

    }

    public UUID extractUserUUID(String token) {
        try {
            // Rimuovere il prefisso "Bearer " se presente
            String tokenClean = token.startsWith("Bearer ") ? token.substring(7) : token;

            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            Key key = Keys.hmacShaKeyFor(keyBytes);

            // Parsing del token pulito
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key)
                    .build().parseClaimsJws(tokenClean);

            String userId = claimsJws.getBody().getSubject();
            System.out.println(userId);
            return UUID.fromString(userId);
        } catch (Exception e) {
            throw new UnauthorizedException(e.toString());
        }
    }
}