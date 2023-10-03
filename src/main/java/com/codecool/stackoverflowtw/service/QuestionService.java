package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.question.BriefQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.DetailedQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.dao.question.QuestionModel;
import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
  private final QuestionsDAO questionsDAO;
  
  @Autowired
  public QuestionService(QuestionsDAO questionsDAO) {
    this.questionsDAO = questionsDAO;
  }
  
  public List<BriefQuestionDTO> getAllQuestions() {
    return questionsDAO.getAllQuestions()
                       .stream()
                       .map(e -> new BriefQuestionDTO(e.id(), e.title(), e.createdAt(), "Teszt Elek", e.answerCount()))
                       .toList();
  }
  
  public Optional<DetailedQuestionDTO> getQuestionById(int id) {
    Optional<QuestionModel> result = questionsDAO.getQuestionById(id);
    return result.map(questionModel -> new DetailedQuestionDTO(questionModel.id(),
                                                               questionModel.title(),
                                                               questionModel.body(),
                                                               0,
                                                               "Teszt Elek",
                                                               questionModel.createdAt()));
  }
  
  public boolean deleteQuestionById(int id) {
    return questionsDAO.deleteQuestionById(id);
  }
  
  public int addNewQuestion(NewQuestionDTO question) {
    return questionsDAO.addNewQuestion(question);
  }
}
