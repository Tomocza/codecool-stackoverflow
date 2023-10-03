package com.codecool.stackoverflowtw.controller.dto.question;

import java.time.LocalDateTime;

public record BriefQuestionDTO(int id, String title, LocalDateTime createdAt, String userName, int answerCount) {
}
