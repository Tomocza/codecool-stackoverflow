package com.codecool.stackoverflowtw.controller.dto.question;

import java.time.LocalDateTime;

public record DetailedQuestionDTO(int id, String title, String body, int rating, String userName,
                                  LocalDateTime createdAt) {
}
