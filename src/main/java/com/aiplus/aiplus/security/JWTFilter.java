package com.aiplus.aiplus.security;

import com.aiplus.aiplus.entities.users.USER_ROLE;
import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.exceptions.UnauthorizedException;
import com.aiplus.aiplus.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match("/admin/**", request.getServletPath()) ||
                pathMatcher.match("/login/**", request.getServletPath()) ||
                pathMatcher.match("/customer/**", request.getServletPath());
    }

    @Bean
    User userPlaceholder(){
        User ritorno = new User();
        ritorno.setRole(USER_ROLE.CUSTOMER);
        ritorno.setName("Customer");
        return ritorno;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore inserisci il token nell'Authorization Header");
        }
        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        Authentication authentication;
        User currentUser;

        if (id == null || id.isEmpty()) {
            currentUser = userPlaceholder();
            authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        } else {
            try {
                currentUser = this.userService.findById(UUID.fromString(id));
                authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            } catch (ChangeSetPersister.NotFoundException e) {
                currentUser = userPlaceholder();
                authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            } catch (IllegalArgumentException e) {
                currentUser = userPlaceholder();
                authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            }
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
