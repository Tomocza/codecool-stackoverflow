package com.codecool.stackoverflowtw.controller.dto.question;

import java.time.LocalDateTime;

public record QuestionDTO(int id, String title, String body, LocalDateTime createdAt, int userId, int answerCount) {
}
