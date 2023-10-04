package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.SessionDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserLoginDTO;
import com.codecool.stackoverflowtw.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("users")
public class UserController {
  private static final String DOMAIN = "localhost";
  private static final int EXPIRY_IN_SECONDS = 3600;
  private static final String SESSION_ID = "session_id";
  private static final String DELETED = "deleted";
  private final UserService userService;
  
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping ("/all")
  public List<UserDTO> getAllUsers() {
    return userService.getAll();
  }
  
  @GetMapping ("/{id}")
  public UserDTO getUserById(@PathVariable int id) {
    return userService.getById(id).orElse(null);
  }
  
  @PostMapping ("/")
  public int register(@RequestBody NewUserDTO user) {
    return userService.register(user);
  }
  
  @PostMapping ("/login")
  public int login(@RequestBody UserLoginDTO user, HttpServletResponse response) {
    Optional<SessionDTO> sessionDTO = userService.login(user);
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
  
  private Cookie generateCookie(String sessionId) {
    Cookie cookie = new Cookie(SESSION_ID, sessionId);
    cookie.setHttpOnly(true);
    cookie.setDomain(DOMAIN);
    cookie.setPath("/");
    cookie.setMaxAge(EXPIRY_IN_SECONDS);
    return cookie;
  }
  
  private Cookie generateDeletedCookie() {
    Cookie cookie = new Cookie(SESSION_ID, DELETED);
    cookie.setMaxAge(0);
    return cookie;
  }
  
  private SessionDTO getSessionDTO(HttpServletResponse response) {
    return new SessionDTO(getUserId(response), response.getHeader("session_id"));
  }
  
  private int getUserId(HttpServletResponse response) {
    return Integer.parseInt(response.getHeader("user_id"));
  }
}
