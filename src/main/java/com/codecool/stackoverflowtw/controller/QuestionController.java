package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.question.NewQuestionDTO;
import com.codecool.stackoverflowtw.controller.dto.question.QuestionDTO;
import com.codecool.stackoverflowtw.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("questions")
public class QuestionController {
  private final QuestionService questionService;
  
  @Autowired
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }
  
  @GetMapping ("/all")
  public List<QuestionDTO> getAllQuestions() {
    return questionService.getAllQuestions();
  }
  
  @GetMapping ("/{id}")
  public QuestionDTO getQuestionById(@PathVariable int id) {
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
}
