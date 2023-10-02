package com.codecool.stackoverflowtw.dao.question;

import com.codecool.stackoverflowtw.controller.dto.NewQuestionDTO;
import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;

import java.util.List;

public class QuestionsDaoJdbc implements QuestionsDAO {
  private final JdbcConnector connector;

  public QuestionsDaoJdbc(JdbcConnector connector) {
    this.connector = connector;
  }

  @Override
  public List<QuestionModel> getAllQuestions() {
    return null;
  }

  @Override
  public QuestionModel getQuestionById(int id) {
    return null;
  }

  @Override
  public int addNewQuestion(NewQuestionDTO newQuestionDTO) {
    return 0;
  }

  @Override
  public boolean deleteQuestionById(int id) {
    return false;
  }
}
