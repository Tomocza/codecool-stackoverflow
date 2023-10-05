package com.codecool.stackoverflowtw.dao.question;

import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionVoteDTO;
import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    String sql =
            "select q.id, q.title, q.body, q.user_id, q.created_at, q.modified_at, count(a.id) as answer_count, sum" +
            "(qv.value) as rating from questions q left join answers a on q.id = a.question_id left join " +
            "question_votes qv on q.id = qv.question_id group by q.id";
    
    try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      
      while (resultSet.next()) {
        result.add(getQuestionFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public List<QuestionModel> getQuestionByName(String name) {
    System.out.println(name);
    List<QuestionModel> result = new ArrayList<>();
    String sql = "SELECT q.id, q.title, q.body, q.user_id, q.created_at, q.modified_at, " +
                 "COUNT(a.id) AS answer_count, SUM(qv.value) AS rating " +
                 "FROM questions q " +
                 "LEFT JOIN answers a ON q.id = a.question_id " +
                 "LEFT JOIN question_votes qv ON q.id = qv.question_id " +
                 "WHERE q.title ILIKE '%'|| ? || '%'" +
                 "GROUP BY q.id";
    try(Connection connection = connector.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()){
          result.add(getQuestionFromResultSet(resultSet));
        }
    }
    catch(SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public Optional<QuestionModel> getQuestionById(int id) {
    String sql =
            "select q.id, q.title, q.body, q.user_id, q.created_at, q.modified_at, count(a.id) as answer_count, sum" +
            "(qv.value) as rating from questions q left join answers a on q.id = a.question_id left join " +
            "question_votes qv on q.id = qv.question_id where q.id = ? group by q.id";
    
    try (Connection connection = connector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      
      if (resultSet.next()) {
        return Optional.of(getQuestionFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return Optional.empty();
  }
  
  @Override
  public int addNewQuestion(NewQuestionDTO newQuestionDTO) {
    int result = -1;
    String sql = "insert into questions(title, body, user_id) values(?,?,?) returning(id)";
    
    try (Connection connection = connector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, newQuestionDTO.title());
      statement.setString(2, newQuestionDTO.body());
      statement.setInt(3, newQuestionDTO.userId());
      ResultSet resultSet = statement.executeQuery();
      
      if (resultSet.next()) {
        result = resultSet.getInt("id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public boolean deleteQuestionById(int id) {
    boolean result = false;
    String sql = "delete from questions where id = ?";
    
    try (Connection connection = connector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      result = statement.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public boolean addVoteToQuestion(QuestionVoteDTO questionVoteDTO) {
    boolean result = false;
    String sql =
            "insert into question_votes(question_id, user_id, value) values(?,?,?) on conflict (question_id, user_id)" +
            " do update set value = ?";
    
    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, questionVoteDTO.questionId());
      pstmt.setInt(2, questionVoteDTO.userId());
      pstmt.setInt(3, questionVoteDTO.value());
      pstmt.setInt(4, questionVoteDTO.value());
      result = pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public boolean deleteQuestionVote(int questionId, int userId) {
    boolean result = false;
    String sql = "delete from question_votes where question_id = ? and user_id = ?";
    
    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, questionId);
      pstmt.setInt(2, userId);
      result = pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  private QuestionModel getQuestionFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String title = resultSet.getString("title");
    String body = resultSet.getString("body");
    int userId = resultSet.getInt("user_id");
    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.SECONDS);
    LocalDateTime modifiedAt = resultSet.getTimestamp("modified_at").toLocalDateTime().truncatedTo(ChronoUnit.SECONDS);
    int answerCount = resultSet.getInt("answer_count");
    int rating = resultSet.getInt("rating");
    return new QuestionModel(id, title, body, userId, createdAt, modifiedAt, answerCount, rating);
  }
}
