package com.aiplus.aiplus.security;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.exceptions.InvalidTokenException;
import com.aiplus.aiplus.exceptions.UnauthorizedException;
import com.aiplus.aiplus.payloads.login.CustomerLoginDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 8)) // 8 ore di vita
                .subject(String.valueOf(user.getId()))
                .claim("role", user.getRole())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))) //Firma con algoritmo HMAC
                .compact();
    }

    public String createCustomerToken(CustomerLoginDTO customerLoginDto) {
        return Jwts.builder()
                .claim("role", "CUSTOMER")
                .claim("tableNumber", customerLoginDto.tavNum())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 min di vita
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))) //Firma con algoritmo HMAC
                .compact();
    }

    public void verifyToken(String token){
        try {
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))).build().parse(token);
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Expired token", 511);
        } catch (SignatureException | MalformedJwtException e) {
            throw new InvalidTokenException("Invalid token", 512);
        } catch (Exception e) {
            throw new InvalidTokenException("Token verification failed", 513);
        }
    }

    public String extractIdFromToken(String token){
        return Jwts.parser().
                verifyWith(Keys.hmacShaKeyFor((secret.getBytes(StandardCharsets.UTF_8)))).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public UUID extractUserUUID(String token) {
        try {
            // Rimuove il prefisso "Bearer " se presente
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
    public int extractTableNumber(String token) {
        String tokenClean = token.startsWith("Bearer ") ? token.substring(7) : token;

        return Jwts.parser()
                 .verifyWith(Keys.hmacShaKeyFor((secret.getBytes(StandardCharsets.UTF_8)))).build()
                .parseClaimsJws(tokenClean)
                .getBody()
                .get("tableNumber", Integer.class);
    }

}