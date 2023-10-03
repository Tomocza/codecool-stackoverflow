package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionDTO;
import com.codecool.stackoverflowtw.dao.question.QuestionModel;
import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                       .map(e -> new QuestionDTO(e.id(),
                                                 e.title(),
                                                 e.body(),
                                                 e.createdAt(),
                                                 e.user_id(),
                                                 e.answerCount()))
                       .toList();
  }

  public Optional<QuestionDTO> getQuestionById(int id) {
    Optional<QuestionModel> result = questionsDAO.getQuestionById(id);
    return result.map(questionModel -> new QuestionDTO(questionModel.id(),
                                                       questionModel.title(),
                                                       questionModel.body(),
                                                       questionModel.createdAt(),
                                                       questionModel.user_id(),
                                                       questionModel.answerCount()));
  }

  public boolean deleteQuestionById(int id) {
    return questionsDAO.deleteQuestionById(id);
  }

  public int addNewQuestion(NewQuestionDTO question) {
    return questionsDAO.addNewQuestion(question);
  }
}
