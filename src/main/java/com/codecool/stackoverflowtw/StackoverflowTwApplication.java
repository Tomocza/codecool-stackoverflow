package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.controller.dto.user.SessionDTO;
import com.codecool.stackoverflowtw.dao.answer.AnswersDAO;
import com.codecool.stackoverflowtw.dao.answer.AnswersDAOJdbc;
import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;
import com.codecool.stackoverflowtw.dao.connection.PsqlConnector;
import com.codecool.stackoverflowtw.dao.question.QuestionsDAO;
import com.codecool.stackoverflowtw.dao.question.QuestionsDaoJdbc;
import com.codecool.stackoverflowtw.dao.user.UsersDAO;
import com.codecool.stackoverflowtw.dao.user.UsersDaoJdbc;
import com.codecool.stackoverflowtw.service.automat.AutomaticExecution;
import com.codecool.stackoverflowtw.service.automat.AutomaticExecutionImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
public class StackoverflowTwApplication {
  public static final int SESSION_EXPIRY_IN_SECONDS = 3600;
  
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
  public AnswersDAO answersDAO(JdbcConnector connector) {
    return new AnswersDAOJdbc(connector);
  }
  
  @Bean
  @Autowired
  public UsersDAO usersDAO(JdbcConnector connector) {
    return new UsersDaoJdbc(connector);
  }
  
  @Bean
  public List<SessionDTO> activeSessions() {
    return new ArrayList<>();
  }
  
  @Bean
  public SecureRandom secureRandom() throws NoSuchAlgorithmException {
    return SecureRandom.getInstanceStrong();
  }
  
  @Bean
  public AutomaticExecution automaticExecution(List<SessionDTO> activeSessions) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    AutomaticExecution automaticExecution = new AutomaticExecutionImpl(scheduler);
    automaticExecution.execute(() -> deleteExpiredSessions(activeSessions));
    return automaticExecution;
  }
  
  private void deleteExpiredSessions(List<SessionDTO> activeSessions) {
    while (!activeSessions.isEmpty() && isFirstElementOldEnough(activeSessions)) {
      activeSessions.remove(0);
    }
  }
  
  private boolean isFirstElementOldEnough(List<SessionDTO> activeSessions) {
    LocalDateTime beginningOfValidStart = LocalDateTime.now().minusSeconds(SESSION_EXPIRY_IN_SECONDS);
    return activeSessions.get(0).time_of_creation().isBefore(beginningOfValidStart);
  }
}
