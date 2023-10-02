package com.codecool.stackoverflowtw.dao.question;

import com.codecool.stackoverflowtw.controller.dto.NewQuestionDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionsDAO {
  List<QuestionModel> getAllQuestions();
  Optional<QuestionModel> getQuestionById(int id);
  int addNewQuestion(NewQuestionDTO newQuestionDTO);
  boolean deleteQuestionById(int id);
}
