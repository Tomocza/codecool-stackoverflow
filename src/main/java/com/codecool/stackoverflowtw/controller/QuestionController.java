package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.question.BriefQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.DetailedQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionVoteDTO;
import com.codecool.stackoverflowtw.service.QuestionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {
  private static final String DOMAIN = "localhost:8080";
  private final QuestionService questionService;

  @Autowired
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @GetMapping("/all")
  public List<BriefQuestionDTO> getAllQuestions() {
    return questionService.getAllQuestions();
  }
  @GetMapping("/search/{title}")
  public List<BriefQuestionDTO> getQuestionsByName(@PathVariable String title){
    return questionService.getQuestionsByName(title);
  }

  @GetMapping("/{qId}/{uId}")
  public DetailedQuestionDTO getQuestionById(@PathVariable int qId, @PathVariable int uId) {
    return questionService.getQuestionById(qId, uId).orElse(null);
  }
  
  @GetMapping ("/{id}")
  public DetailedQuestionDTO getQuestionById(@PathVariable int id) {
    return questionService.getQuestionById(id).orElse(null);
  }

  @PostMapping("/")
  public int addNewQuestion(@RequestBody NewQuestionDTO question, HttpServletResponse response) {
    if (question.userId() != getUserId(response)) {
      return -1;
    }
    return questionService.addNewQuestion(question);
  }

  @DeleteMapping("/{id}")
  public boolean deleteQuestionById(@PathVariable int id) {
    return questionService.deleteQuestionById(id);
  }

  @PostMapping("/votes")
  public int addVoteToQuestion(@RequestBody QuestionVoteDTO questionVoteDTO, HttpServletResponse response) {
    if (questionVoteDTO.userId() != getUserId(response)) {
      return -1;
    }
    return questionService.addVoteToQuestion(questionVoteDTO);
  }

  @DeleteMapping("/votes/{qId}/{uId}")
  public int deleteQuestionVote(@PathVariable int qId, @PathVariable int uId, HttpServletResponse response) {
    if (uId != getUserId(response)) {
      return -1;
    }
    return questionService.deleteQuestionVote(qId, uId);
  }

  private int getUserId(HttpServletResponse response) {
    return Integer.parseInt(response.getHeader("user_id"));
  }
}
