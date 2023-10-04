package com.codecool.stackoverflowtw.dao.question;

import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionVoteDTO;
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
    String sql = "select q.id, q.title, q.body, q.user_id, q.created_at, q.modified_at, count(a.id) as answer_count from " + "questions q left join public.answers a on q.id = a.question_id group by q.id";

    try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);

      while (resultSet.next()) {
        result.add(getQuestionFromResultSet(resultSet));
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  public Optional<QuestionModel> getQuestionById(int id) {
    String sql = "select q.id, q.title, q.body, q.user_id, q.created_at, q.modified_at, count(a.id) as answer_count from " + "questions q left join public.answers a on q.id = a.question_id where q.id = ? group by q.id";

    try (Connection connection = connector.getConnection(); PreparedStatement statement = connection.prepareStatement(
            sql)) {
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return Optional.of(getQuestionFromResultSet(resultSet));
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

    try (Connection connection = connector.getConnection(); PreparedStatement statement = connection.prepareStatement(
            sql)) {
      statement.setString(1, newQuestionDTO.title());
      statement.setString(2, newQuestionDTO.body());
      statement.setInt(3, newQuestionDTO.userId());
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        result = resultSet.getInt("id");
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

    try (Connection connection = connector.getConnection(); PreparedStatement statement = connection.prepareStatement(
            sql)) {
      statement.setInt(1, id);
      result = statement.executeUpdate() > 0;
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  public boolean addVoteToQuestion(QuestionVoteDTO questionVoteDTO) {
    boolean result = false;
    String sql = "insert into question_votes(question_id, user_id, value) values(?,?,?)";

    try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, questionVoteDTO.questionId());
      pstmt.setInt(2, questionVoteDTO.userId());
      pstmt.setInt(3, questionVoteDTO.value());
      result = pstmt.executeUpdate() > 0;
    }
    catch (SQLException e) {
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
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  private QuestionModel getQuestionFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String title = resultSet.getString("title");
    String body = resultSet.getString("body");
    int userId = resultSet.getInt("user_id");
    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    LocalDateTime modifiedAt = resultSet.getTimestamp("modified_at").toLocalDateTime();
    int answerCount = resultSet.getInt("answer_count");
    return new QuestionModel(id, title, body, userId, createdAt, modifiedAt, answerCount);
  }
}
