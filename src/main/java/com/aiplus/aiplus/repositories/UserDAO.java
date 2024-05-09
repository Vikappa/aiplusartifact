package com.aiplus.aiplus.repositories;

import com.aiplus.aiplus.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDAO extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

}
