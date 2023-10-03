package com.codecool.stackoverflowtw.dao.answer;

import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;
import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnswersDAOJdbc implements AnswersDAO {
  private final JdbcConnector connector;
  
  public AnswersDAOJdbc(JdbcConnector connector) {
    this.connector = connector;
  }
  
  @Override
  public List<AnswerModel> getAnswersByQuestionId(int questionId) {
    List<AnswerModel> result = new ArrayList<>();
    String sql =
            "select a.id, a.question_id, a.body, a.user_id, a.created_at, a.modified_at, a.accepted from answers a " +
            "where a.question_id = ?";
    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, questionId);
      ResultSet rs = pstmt.executeQuery();
      
      if (rs.next()) {
        result.add(getAnswerFromResultSet(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return result;
  }
  
  @Override
  public int addNewAnswer(NewAnswerDTO newAnswerDTO) {
    int result = -1;
    String sql = "insert into answers(body, user_id, question_id) values(?,?,?) returning id";
    
    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, newAnswerDTO.body());
      pstmt.setInt(2, newAnswerDTO.userId());
      pstmt.setInt(3, newAnswerDTO.questionId());
      ResultSet rs = pstmt.executeQuery();
      
      if (rs.next()) {
        result = rs.getInt("id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public boolean deleteAnswerById(int id) {
    boolean result = false;
    String sql = "delete from answers where id = ?";
    
    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      result = pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  private AnswerModel getAnswerFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    int questionId = rs.getInt("question_id");
    String body = rs.getString("body");
    int userId = rs.getInt("user_id");
    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
    LocalDateTime modifiedAt = rs.getTimestamp("modified_at").toLocalDateTime();
    boolean accepted = rs.getBoolean("accepted");
    return new AnswerModel(id, questionId, body, userId, createdAt, modifiedAt, accepted);
  }
}
