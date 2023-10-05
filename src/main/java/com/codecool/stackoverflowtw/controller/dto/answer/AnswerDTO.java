package com.codecool.stackoverflowtw.controller.dto.answer;

import java.time.LocalDateTime;

public record AnswerDTO(int id, String body, String userName, LocalDateTime createdAt, boolean accepted, int rating,
                        boolean hasVoted) {
}
