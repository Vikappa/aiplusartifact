package com.aiplus.aiplus.security;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.exceptions.UnauthorizedException;
import com.aiplus.aiplus.repositories.UserDAO;
import com.aiplus.aiplus.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    UserService userService;
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    UserDAO userDAO;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return  pathMatcher.match("/admin/**", request.getServletPath()) ||
                pathMatcher.match("/login/**", request.getServletPath()) ||
                pathMatcher.match("/customer/**", request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Il codice di questo metodo verrà eseguito ad ogni richiesta che richieda di essere autenticati
        // Cose da fare:

        // 1. Controlliamo se nella richiesta corrente ci sia un Authorization Header, se non c'è --> 401
        String authHeader = request.getHeader("Authorization"); // Authorization Header --> Bearer eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTMxNzY3NDUsImV4cCI6MTcxMzc4MTU0NSwic3ViIjoiZDFlZTlmN2MtZWQwZS00ZTQ3LThmN2EtYTQ0Yzk5MTNkMzE0In0.HFk14O-60faQY4TEnvsNgqjQdOVy7aD-1L-jCvayGz2VTRIQQqGDRzx1qSx5WWxy

        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Per favore inserisci il token nell'Authorization Header");

        // 2. Se c'è estraiamo il token dall'header
        String accessToken = authHeader.substring(7);

        // 3. Verifichiamo se il token è stato manipolato (verifica della signature) e se non è scaduto (verifica Expiration Date)
        jwtTools.verifyToken(accessToken);

        // 4. Se tutto è OK andiamo al prossimo elemento della Filter Chain, per prima o poi arrivare all'endpoint

        // 4.1 Cerco l'utente nel DB tramite id (l'id sta nel token..)
        String id = jwtTools.extractIdFromToken(accessToken);
        User currentUser = null;
        try {
            currentUser = this.userService.findById(UUID.fromString(id));
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        // 4.2 Devo informare Spring Security su chi sia l'utente corrente che sta effettuando la richiesta. In qualche maniera
        // equivale ad "associare" l'utente alla richiesta corrente
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        // OBBLIGATORIO il terzo parametro con la lista ruoli dell'utente se si vuol poter usare i vari @PreAuthorize
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4.3 Vado al prossimo elemento della catena, passandogli gli oggetti request e response
        filterChain.doFilter(request, response);
        // 5. Se il token non fosse OK --> 401
    }

}
