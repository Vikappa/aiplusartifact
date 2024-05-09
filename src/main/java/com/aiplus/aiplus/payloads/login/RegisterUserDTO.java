package com.aiplus.aiplus.payloads.login;

import com.aiplus.aiplus.entities.users.USER_ROLE;

public record RegisterUserDTO(
        String name,
        String surname,
        String email,
        String password,
        USER_ROLE role
) {
}
