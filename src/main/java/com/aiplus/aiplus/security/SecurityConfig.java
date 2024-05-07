package com.aiplus.aiplus.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(http -> http.disable()) // Disabilita il form di login
                .csrf(http -> http.disable()) // Disabilita la protezione da CSRF
                .sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Disabilita le sessioni
                .authorizeRequests(http -> http.requestMatchers("/**").permitAll()) // Disabilita il meccanismo di autenticazione di default
                .exceptionHandling(http -> http
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND))); // Imposta l'entry point per la risposta 404
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

}
