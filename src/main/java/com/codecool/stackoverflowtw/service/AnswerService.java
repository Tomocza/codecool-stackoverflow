package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.answer.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;
import com.codecool.stackoverflowtw.dao.answer.AnswersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
  private final AnswersDAO answersDAO;

  @Autowired
  public AnswerService(AnswersDAO answersDAO) {
    this.answersDAO = answersDAO;
  }

  public List<AnswerDTO> getAnswersByQuestionId(int questionId) {
    return answersDAO.getAnswersByQuestionId(questionId)
                     .stream()
                     .map(e -> new AnswerDTO(e.id(), e.body(), "Teszt Elek", e.createdAt(), e.accepted()))
                     .toList();
  }

  public int addNewAnswer(NewAnswerDTO newAnswerDTO) {
    return answersDAO.addNewAnswer(newAnswerDTO);
  }

  public boolean deleteAnswerById(int id) {
    return answersDAO.deleteAnswerById(id);
  }
}
