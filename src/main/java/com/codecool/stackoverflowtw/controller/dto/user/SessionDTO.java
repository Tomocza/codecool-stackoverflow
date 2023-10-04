package com.codecool.stackoverflowtw.controller.dto.user;

import java.time.LocalDateTime;
import java.util.Objects;

public record SessionDTO(int user_id, String session_id, LocalDateTime time_of_creation) {
  public SessionDTO(int user_id, String session_id) {
    this(user_id, session_id, LocalDateTime.now());
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SessionDTO that = (SessionDTO) o;
    return user_id == that.user_id && Objects.equals(session_id, that.session_id);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(user_id, session_id);
  }
}
