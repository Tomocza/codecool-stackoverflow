package com.codecool.stackoverflowtw.dao.answer;

import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;

import java.util.List;

public interface AnswersDAO {
  List<AnswerModel> getAnswersByQuestionId(int questionId);
  int addNewAnswer(NewAnswerDTO newAnswerDTO);
  boolean deleteAnswerById(int id);
}
