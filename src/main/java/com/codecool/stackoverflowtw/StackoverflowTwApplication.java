package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import com.codecool.stackoverflowtw.dao.question.QuestionsDaoJdbc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StackoverflowTwApplication {
  public static void main(String[] args) {
    SpringApplication.run(StackoverflowTwApplication.class, args);
  }

  @Bean
  public QuestionsDAO questionsDAO() {
    return new QuestionsDaoJdbc();
  }
}
