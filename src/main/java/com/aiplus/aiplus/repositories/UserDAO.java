package com.aiplus.aiplus.repositories;
import org.springframework.data.jpa.repository.Query;

import com.aiplus.aiplus.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findByUUID(UUID id);
}
