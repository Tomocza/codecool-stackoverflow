package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.answer.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.AnswerVoteDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;
import com.codecool.stackoverflowtw.dao.answer.AnswerModel;
import com.codecool.stackoverflowtw.dao.answer.AnswersDAO;
import com.codecool.stackoverflowtw.dao.user.UserModel;
import com.codecool.stackoverflowtw.dao.user.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
  private final AnswersDAO answersDAO;
  private final UsersDAO usersDAO;

  @Autowired
  public AnswerService(AnswersDAO answersDAO, UsersDAO usersDAO) {
    this.answersDAO = answersDAO;
    this.usersDAO = usersDAO;
  }

  public List<AnswerDTO> getAnswersByQuestionId(int questionId, int userId) {
    return answersDAO.getAnswersByQuestionId(questionId, userId)
                     .stream()
                     .map(e -> new AnswerDTO(e.id(),
                                             e.body(),
                                             getUsername(e.userId()),
                                             e.createdAt(),
                                             e.accepted(),
                                             e.rating(),
                                             e.hasVoted()))
                     .toList();
  }

  public int addNewAnswer(NewAnswerDTO newAnswerDTO) {
    return answersDAO.addNewAnswer(newAnswerDTO);
  }

  public boolean deleteAnswerById(int id) {
    return answersDAO.deleteAnswerById(id);
  }

  public int addVoteToAnswer(AnswerVoteDTO answerVoteDTO) {
    int result = 0;
    if (answersDAO.addVoteToAnswer(answerVoteDTO)) {
      Optional<AnswerModel> updatedAnswerData = answersDAO.getAnswerById(answerVoteDTO.answerId());
      result = updatedAnswerData.map(AnswerModel::rating).orElse(0);
    }
    return result;
  }

  public int deleteAnswerVote(int answerId, int userId) {
    int result = 0;
    if (answersDAO.deleteAnswerVote(answerId, userId)) {
      Optional<AnswerModel> updatedAnswerData = answersDAO.getAnswerById(answerId);
      result = updatedAnswerData.map(AnswerModel::rating).orElse(0);
    }
    return result;
  }

  private String getUsername(int user_id) {
    return usersDAO.getById(user_id).orElseGet(() -> new UserModel(0, "anonymous", "", null)).username();
  }
}
