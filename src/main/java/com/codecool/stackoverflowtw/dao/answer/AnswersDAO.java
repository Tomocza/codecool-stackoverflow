package com.codecool.stackoverflowtw.dao.answer;

import com.codecool.stackoverflowtw.controller.dto.answer.AnswerVoteDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;

import java.util.List;
import java.util.Optional;

public interface AnswersDAO {
  List<AnswerModel> getAnswersByQuestionId(int questionId);
  Optional<AnswerModel> getAnswerById(int answerId);
  int addNewAnswer(NewAnswerDTO newAnswerDTO);
  boolean deleteAnswerById(int id);
  boolean addVoteToAnswer(AnswerVoteDTO answerVoteDTO);
  boolean deleteAnswerVote(int answerId, int userId);
}
