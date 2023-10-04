package com.codecool.stackoverflowtw.controller.dto.user;

import java.time.LocalDateTime;

public record SessionDTO(int user_id, String session_id, LocalDateTime time_of_creation) {
  public SessionDTO(int user_id, String session_id) {
    this(user_id, session_id, LocalDateTime.now());
  }
}
