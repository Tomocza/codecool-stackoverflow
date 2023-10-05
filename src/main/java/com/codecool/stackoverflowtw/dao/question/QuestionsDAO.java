package com.codecool.stackoverflowtw.dao.question;

import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionVoteDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionsDAO {
  List<QuestionModel> getAllQuestions();
  Optional<QuestionModel> getQuestionById(int id, int currUserId);
  int addNewQuestion(NewQuestionDTO newQuestionDTO);
  boolean deleteQuestionById(int id);
  boolean addVoteToQuestion(QuestionVoteDTO questionVoteDTO);
  boolean deleteQuestionVote(int questionId, int userId);
}
