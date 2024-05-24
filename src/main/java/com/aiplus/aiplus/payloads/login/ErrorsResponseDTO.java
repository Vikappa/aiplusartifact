package com.aiplus.aiplus.payloads.login;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(String message, LocalDateTime timestamp, int errorCode) {}
