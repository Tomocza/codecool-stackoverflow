package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;
import com.codecool.stackoverflowtw.dao.connection.PsqlConnector;
import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import com.codecool.stackoverflowtw.dao.question.QuestionsDaoJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StackoverflowTwApplication {
  public static void main(String[] args) {
    SpringApplication.run(StackoverflowTwApplication.class, args);
  }

  @Bean
  public JdbcConnector connector() {
    return new PsqlConnector("localhost", "5432", "cc_stackoverflow", "Tomi", "masterkey");
  }

  @Bean
  @Autowired
  public QuestionsDAO questionsDAO(JdbcConnector connector) {
    return new QuestionsDaoJdbc(connector);
  }
}
