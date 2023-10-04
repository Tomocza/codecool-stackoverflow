package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.question.BriefQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.DetailedQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionVoteDTO;
import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;
import com.codecool.stackoverflowtw.service.QuestionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("questions")
public class QuestionController {
  private static final String DOMAIN = "localhost:8080";
  private final QuestionService questionService;
  
  @Autowired
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }
  
  @GetMapping ("/all")
  public List<BriefQuestionDTO> getAllQuestions() {
    return questionService.getAllQuestions();
  }
  
  @GetMapping ("/{id}")
  public DetailedQuestionDTO getQuestionById(@PathVariable int id) {
    return questionService.getQuestionById(id).orElse(null);
  }
  
  @PostMapping ("/")
  public int addNewQuestion(@RequestBody NewQuestionDTO question) {
    return questionService.addNewQuestion(question);
  }
  
  @DeleteMapping ("/{id}")
  public boolean deleteQuestionById(@PathVariable int id) {
    return questionService.deleteQuestionById(id);
  }
  
  @PostMapping ("/votes")
  public int addVoteToQuestion(@RequestBody QuestionVoteDTO questionVoteDTO) {
    return questionService.addVoteToQuestion(questionVoteDTO);
  }
  
  @DeleteMapping ("/votes/{qId}/{uId}")
  public int deleteQuestionVote(@PathVariable int qId, @PathVariable int uId) {
    return questionService.deleteQuestionVote(qId, uId);
  }
}
