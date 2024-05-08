package com.aiplus.aiplus.payloads.login;
import com.aiplus.aiplus.entities.users.USER_ROLE;

public record UserLoginResponseDTO(String accessToken, USER_ROLE role) {
}
