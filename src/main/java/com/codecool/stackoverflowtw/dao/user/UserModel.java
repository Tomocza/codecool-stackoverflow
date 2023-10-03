package com.codecool.stackoverflowtw.dao.user;

import java.time.LocalDateTime;

public record UserModel(int id, String username, String pwHash, LocalDateTime registeredAt) {
}
