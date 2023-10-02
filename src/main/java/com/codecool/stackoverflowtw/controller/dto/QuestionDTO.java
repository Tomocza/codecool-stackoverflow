package com.codecool.stackoverflowtw.controller.dto;

import java.time.LocalDateTime;

public record QuestionDTO(int id, String title, String body, LocalDateTime createdAt) {
}
