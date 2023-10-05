package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.StackoverflowTwApplication;
import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.SessionDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserLoginDTO;
import com.codecool.stackoverflowtw.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("users")
public class UserController {
  private static final String SESSION_ID = "session_id";
  private static final String DELETED = "deleted";
  private final UserService userService;
  
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping ("/all")
  public List<UserDTO> getAll() {
    return userService.getAll();
  }
  
  @GetMapping ("/{id}")
  public UserDTO getById(@PathVariable int id) {
    return userService.getById(id).orElse(null);
  }
  
  @PostMapping ("/")
  public int register(@RequestBody NewUserDTO user, HttpServletResponse response) {
    Optional<SessionDTO> sessionDTO = userService.register(user);
    if (sessionDTO.isEmpty()) {
      return -1;
    }
    String sessionId = sessionDTO.get().session_id();
    Cookie cookie = generateCookie(sessionId);
    response.addCookie(cookie);
    return sessionDTO.get().user_id();
  }
  
  @PostMapping ("/login")
  public int login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
    Optional<SessionDTO> sessionDTO = userService.login(userLoginDTO);
    if (sessionDTO.isEmpty()) {
      return -1;
    }
    String sessionId = sessionDTO.get().session_id();
    Cookie cookie = generateCookie(sessionId);
    response.addCookie(cookie);
    return sessionDTO.get().user_id();
  }
  
  @DeleteMapping ("/logout")
  public boolean logout(HttpServletResponse response) {
    SessionDTO sessionDTO = getSessionDTO(response);
    Cookie cookie = generateDeletedCookie();
    response.addCookie(cookie);
    return userService.logout(sessionDTO);
  }
  
  @DeleteMapping ("/{id}")
  public boolean deleteUserById(@PathVariable int id) {
    return userService.deleteById(id);
  }
  @GetMapping("/session")
  public int userIdBySession(@CookieValue String session_id){
    return userService.hasValidSession(session_id);
  }
  
  private Cookie generateCookie(String sessionId) {
    Cookie cookie = new Cookie(SESSION_ID, sessionId);
    cookie.setHttpOnly(true);
    Dotenv dotenv = Dotenv.load();
    cookie.setDomain(dotenv.get("DB_HOST"));
    cookie.setPath("/");
    cookie.setMaxAge(StackoverflowTwApplication.SESSION_EXPIRY_IN_SECONDS);
    return cookie;
  }
  
  private Cookie generateDeletedCookie() {
    Cookie cookie = new Cookie(SESSION_ID, DELETED);
    cookie.setHttpOnly(true);
    Dotenv dotenv = Dotenv.load();
    cookie.setDomain(dotenv.get("DB_HOST"));
    cookie.setPath("/");
    cookie.setMaxAge(1);
    return cookie;
  }
  
  private SessionDTO getSessionDTO(HttpServletResponse response) {
    return new SessionDTO(getUserId(response), response.getHeader("session_id"));
  }
  
  private int getUserId(HttpServletResponse response) {
    return Integer.parseInt(response.getHeader("user_id"));
  }
}
