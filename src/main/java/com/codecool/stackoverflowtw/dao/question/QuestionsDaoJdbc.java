package com.codecool.stackoverflowtw.dao.question;

import com.codecool.stackoverflowtw.controller.dto.NewQuestionDTO;
import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionsDaoJdbc implements QuestionsDAO {
  private final JdbcConnector connector;

  public QuestionsDaoJdbc(JdbcConnector connector) {
    this.connector = connector;
  }

  @Override
  public List<QuestionModel> getAllQuestions() {
    List<QuestionModel> result = new ArrayList<>();
    String sql = "select * from questions";

    try (Connection conn = connector.getConnection()) {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        result.add(getQuestionFromResultSet(rs));
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  public Optional<QuestionModel> getQuestionById(int id) {
    String sql = "select * from questions where id = ?";

    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return Optional.of(getQuestionFromResultSet(rs));
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return Optional.empty();
  }

  @Override
  public int addNewQuestion(NewQuestionDTO newQuestionDTO) {
    int result = -1;
    String sql = "insert into questions(title, body, user_id) values(?,?,?) returning(id)";

    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, newQuestionDTO.title());
      pstmt.setString(2, newQuestionDTO.body());
      pstmt.setInt(3, newQuestionDTO.userId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt("id");
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  public boolean deleteQuestionById(int id) {
    boolean result = false;
    String sql = "delete from questions where id = ?";

    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      result = pstmt.executeUpdate() > 0;
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  private QuestionModel getQuestionFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String title = rs.getString("title");
    String body = rs.getString("body");
    int userId = rs.getInt("user_id");
    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
    LocalDateTime modifiedAt = rs.getTimestamp("modified_at").toLocalDateTime();
    return new QuestionModel(id, title, body, userId, createdAt, modifiedAt);
  }
}
