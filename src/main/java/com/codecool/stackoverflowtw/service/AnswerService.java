package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.answer.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.AnswerVoteDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;
import com.codecool.stackoverflowtw.dao.answer.AnswersDAO;
import com.codecool.stackoverflowtw.dao.user.UserModel;
import com.codecool.stackoverflowtw.dao.user.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
  private final AnswersDAO answersDAO;
  private final UsersDAO usersDAO;

  @Autowired
  public AnswerService(AnswersDAO answersDAO, UsersDAO usersDAO) {
    this.answersDAO = answersDAO;
    this.usersDAO = usersDAO;
  }

  public List<AnswerDTO> getAnswersByQuestionId(int questionId) {
    return answersDAO.getAnswersByQuestionId(questionId)
                     .stream()
                     .map(e -> new AnswerDTO(e.id(),
                                             e.body(),
                                             getUsername(e.userId()),
                                             e.createdAt(),
                                             e.accepted(),
                                             e.rating()))
                     .toList();
  }

  public int addNewAnswer(NewAnswerDTO newAnswerDTO) {
    return answersDAO.addNewAnswer(newAnswerDTO);
  }

  public boolean deleteAnswerById(int id) {
    return answersDAO.deleteAnswerById(id);
  }

  public boolean addVoteToAnswer(AnswerVoteDTO answerVoteDTO) {
    return answersDAO.addVoteToAnswer(answerVoteDTO);
  }

  public boolean deleteAnswerVote(int answerId, int userId) {
    return answersDAO.deleteAnswerVote(answerId, userId);
  }

  private String getUsername(int user_id) {
    return usersDAO.getById(user_id).orElseGet(() -> new UserModel(0, "anonymous", "", null)).username();
  }
}
