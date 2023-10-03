package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;
import com.codecool.stackoverflowtw.dao.connection.PsqlConnector;
import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import com.codecool.stackoverflowtw.dao.question.QuestionsDaoJdbc;
import com.codecool.stackoverflowtw.dao.user.UsersDAO;
import com.codecool.stackoverflowtw.dao.user.UsersDaoJdbc;
import io.github.cdimascio.dotenv.Dotenv;
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
    Dotenv dotenv = Dotenv.load();
    String host = dotenv.get("DB_HOST");
    String port = dotenv.get("DB_PORT");
    String name = dotenv.get("DB_NAME");
    String username = dotenv.get("DB_USER_NAME");
    String password = dotenv.get("DB_PW");
    return new PsqlConnector(host, port, name, username, password);
  }
  
  @Bean
  @Autowired
  public QuestionsDAO questionsDAO(JdbcConnector connector) {
    return new QuestionsDaoJdbc(connector);
  }
  
  @Bean
  @Autowired
  public UsersDAO usersDAO(JdbcConnector connector) {
    return new UsersDaoJdbc(connector);
  }
}
