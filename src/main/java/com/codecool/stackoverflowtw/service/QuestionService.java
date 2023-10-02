package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.QuestionDTO;
import com.codecool.stackoverflowtw.dao.question.QuestionModel;
import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
  private QuestionsDAO questionsDAO;

  @Autowired
  public QuestionService(QuestionsDAO questionsDAO) {
    this.questionsDAO = questionsDAO;
  }

  public List<QuestionDTO> getAllQuestions() {
    return questionsDAO.getAllQuestions()
                       .stream()
                       .map(e -> new QuestionDTO(e.id(), e.title(), e.body(), e.createdAt()))
                       .toList();
  }

  public QuestionDTO getQuestionById(int id) {
    QuestionModel result = questionsDAO.getQuestionById(id);
    return new QuestionDTO(result.id(), result.title(), result.body(), result.createdAt());
  }

  public boolean deleteQuestionById(int id) {
    return questionsDAO.deleteQuestionById(id);
  }

  public int addNewQuestion(NewQuestionDTO question) {
    return questionsDAO.addNewQuestion(question);
  }
}
