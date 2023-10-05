package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.answer.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.AnswerVoteDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;
import com.codecool.stackoverflowtw.service.AnswerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("answers")
public class AnswerController {
  private final AnswerService answerService;
  
  @Autowired
  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }
  
  @GetMapping ("/question/{id}")
  public List<AnswerDTO> getAnswersByQuestionId(@PathVariable int id) {
    return answerService.getAnswersByQuestionId(id);
  }
  
  @PostMapping ("/")
  public int addNewAnswer(@RequestBody NewAnswerDTO newAnswerDTO, HttpServletResponse response) {
    if (newAnswerDTO.userId() != getUserId(response)) {
      return -1;
    }
    return answerService.addNewAnswer(newAnswerDTO);
  }
  
  @DeleteMapping ("/{id}")
  public boolean deleteAnswerById(@PathVariable int id) {
    return answerService.deleteAnswerById(id);
  }
  
  @PostMapping ("/votes")
  public int addVoteToAnswer(@RequestBody AnswerVoteDTO answerVoteDTO, HttpServletResponse response) {
    if (answerVoteDTO.userId() != getUserId(response)) {
      return -1;
    }
    return answerService.addVoteToAnswer(answerVoteDTO);
  }
  
  @DeleteMapping ("/votes/{aId}/{uId}")
  public int deleteAnswerVote(@PathVariable int aId, @PathVariable int uId, HttpServletResponse response) {
    if (uId != getUserId(response)) {
      return -1;
    }
    return answerService.deleteAnswerVote(aId, uId);
  }
  
  private int getUserId(HttpServletResponse response) {
    return Integer.parseInt(response.getHeader("user_id"));
  }
}
