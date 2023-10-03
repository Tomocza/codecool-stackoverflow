package com.codecool.stackoverflowtw.dao.question;

import java.time.LocalDateTime;

public record QuestionModel(int id, String title, String body, int user_id, LocalDateTime createdAt,
                            LocalDateTime modifiedAt, int answerCount) {
}
