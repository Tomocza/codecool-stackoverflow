package com.codecool.stackoverflowtw.dao.question;

import com.codecool.stackoverflowtw.controller.dto.NewQuestionDTO;

import java.util.List;

public interface QuestionsDAO {
  List<QuestionModel> getAllQuestions();
  QuestionModel getQuestionById(int id);
  int addNewQuestion(NewQuestionDTO newQuestionDTO);
  boolean deleteQuestionById(int id);
}
