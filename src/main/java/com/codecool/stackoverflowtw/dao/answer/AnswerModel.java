package com.codecool.stackoverflowtw.dao.answer;

import java.time.LocalDateTime;

public record AnswerModel(int id, int questionId, String body, int userId, LocalDateTime createdAt,
                          LocalDateTime modifiedAt, boolean accepted, int rating, boolean hasVoted) {
}
