package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.answer.AnswerDTO;
import com.codecool.stackoverflowtw.controller.dto.answer.NewAnswerDTO;
import com.codecool.stackoverflowtw.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("answers")
public class AnswerController {
  private final AnswerService answerService;

  @Autowired
  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @GetMapping("/question/{id}")
  public List<AnswerDTO> getAnswersByQuestionId(@PathVariable int id) {
    return answerService.getAnswersByQuestionId(id);
  }

  @PostMapping("/")
  public int addNewAnswer(@RequestBody NewAnswerDTO newAnswerDTO) {
    return answerService.addNewAnswer(newAnswerDTO);
  }

  @DeleteMapping("/{id}")
  public boolean deleteAnswerById(@PathVariable int id) {
    return answerService.deleteAnswerById(id);
  }
}
