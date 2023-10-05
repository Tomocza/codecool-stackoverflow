package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.question.BriefQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.DetailedQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionVoteDTO;
import com.codecool.stackoverflowtw.dao.question.QuestionModel;
import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import com.codecool.stackoverflowtw.dao.user.UserModel;
import com.codecool.stackoverflowtw.dao.user.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
  private final QuestionsDAO questionsDAO;
  private final UsersDAO usersDAO;
  
  @Autowired
  public QuestionService(QuestionsDAO questionsDAO, UsersDAO usersDAO) {
    this.questionsDAO = questionsDAO;
    this.usersDAO = usersDAO;
  }
  
  public List<BriefQuestionDTO> getQuestionsByName(String name) {
    return questionsDAO.getQuestionByName(name)
                       .stream()
                       .map(e -> new BriefQuestionDTO(e.id(),
                                                      e.title(),
                                                      e.createdAt(),
                                                      getUsername(e.user_id()),
                                                      e.answerCount(),
                                                      e.rating()))
                       .toList();
  }
  
  public List<BriefQuestionDTO> getAllQuestions() {
    return questionsDAO.getAllQuestions()
                       .stream()
                       .map(e -> new BriefQuestionDTO(e.id(),
                                                      e.title(),
                                                      e.createdAt(),
                                                      getUsername(e.user_id()),
                                                      e.answerCount(),
                                                      e.rating()))
                       .toList();
  }
  
  public Optional<DetailedQuestionDTO> getQuestionById(int id, int currUserId) {
    Optional<QuestionModel> result = questionsDAO.getQuestionById(id, currUserId);
    return result.map(questionModel -> new DetailedQuestionDTO(questionModel.id(),
                                                               questionModel.title(),
                                                               questionModel.body(),
                                                               questionModel.rating(),
                                                               getUsername(questionModel.user_id()),
                                                               questionModel.createdAt(),
                                                               questionModel.hasVoted()));
  }
  
  public boolean deleteQuestionById(int id) {
    return questionsDAO.deleteQuestionById(id);
  }
  
  public int addNewQuestion(NewQuestionDTO question) {
    return questionsDAO.addNewQuestion(question);
  }
  
  public int addVoteToQuestion(QuestionVoteDTO questionVoteDTO) {
    int result = 0;
    if (questionsDAO.addVoteToQuestion(questionVoteDTO)) {
      Optional<QuestionModel> updatedQuestionData =
              questionsDAO.getQuestionById(questionVoteDTO.questionId(), questionVoteDTO.userId());
      result = updatedQuestionData.map(QuestionModel::rating).orElse(0);
    }
    return result;
  }
  
  public int deleteQuestionVote(int questionId, int userId) {
    int result = 0;
    if (questionsDAO.deleteQuestionVote(questionId, userId)) {
      Optional<QuestionModel> updatedQuestionData = questionsDAO.getQuestionById(questionId, userId);
      result = updatedQuestionData.map(QuestionModel::rating).orElse(0);
    }
    return result;
  }
  
  private String getUsername(int user_id) {
    return usersDAO.getById(user_id).orElseGet(() -> new UserModel(0, "anonymous", "", null)).username();
  }
}
