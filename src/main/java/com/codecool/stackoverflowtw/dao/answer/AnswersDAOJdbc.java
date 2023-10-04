package com.codecool.stackoverflowtw.dao.answer;

import com.codecool.stackoverflowtw.controller.dto.answer.AnswerVoteDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;
import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnswersDAOJdbc implements AnswersDAO {
  private final JdbcConnector connector;
  
  public AnswersDAOJdbc(JdbcConnector connector) {
    this.connector = connector;
  }
  
  @Override
  public List<AnswerModel> getAnswersByQuestionId(int questionId) {
    List<AnswerModel> result = new ArrayList<>();
    String sql =
            "select a.id, a.question_id, a.body, a.user_id, a.created_at, a.modified_at, a.accepted, sum(av.value) as" +
            " rating from answers a left join answer_votes av on a.id = av.answer_id where a.question_id = ? group by" +
            " a.id";
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
  public Optional<AnswerModel> getAnswerById(int answerId) {
    String sql = "select a.id, a.question_id, a.body, a.user_id, a.created_at, a.modified_at, a.accepted, sum(av.value) as rating from answers a left join answer_votes av on a.id = av.answer_id where a.id = ? group by a.id";

    try (Connection connection = connector.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, answerId);
      ResultSet resultSet = pstmt.executeQuery();

      if (resultSet.next()) {
        return Optional.of(getAnswerFromResultSet(resultSet));
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return Optional.empty();
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
  
  @Override
  public boolean addVoteToAnswer(AnswerVoteDTO answerVoteDTO) {
    boolean result = false;
    String sql = "insert into answer_votes(answer_id, user_id, value) values(?,?,?) on conflict (answer_id, user_id) do update set value = ?";

    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, answerVoteDTO.answerId());
      pstmt.setInt(2, answerVoteDTO.userId());
      pstmt.setInt(3, answerVoteDTO.value());
      pstmt.setInt(4, answerVoteDTO.value());
      result = pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public boolean deleteAnswerVote(int answerId, int userId) {
    boolean result = false;
    String sql = "delete from answer_votes where answer_id = ? and user_id = ?";
    
    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, answerId);
      pstmt.setInt(2, userId);
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
    int rating = rs.getInt("rating");
    return new AnswerModel(id, questionId, body, userId, createdAt, modifiedAt, accepted, rating);
  }
}
