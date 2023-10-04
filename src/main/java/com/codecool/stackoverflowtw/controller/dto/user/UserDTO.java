package com.codecool.stackoverflowtw.controller.dto.user;

import java.time.LocalDateTime;

public record UserDTO(int id, String username, LocalDateTime registeredAt) {
}
