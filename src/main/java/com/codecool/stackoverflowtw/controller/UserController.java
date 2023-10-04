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
    Cookie cookie = new Cookie("session_id", sessionId);
    cookie.setHttpOnly(true);
    cookie.setDomain(DOMAIN);
    cookie.setPath("/");
    cookie.setMaxAge(EXPIRY_IN_SECONDS);
    response.addCookie(cookie);
    return sessionDTO.get().user_id();
  }
  
  @DeleteMapping ("/logout")
  public boolean logout(HttpServletResponse response) {
    return userService.logout(Integer.parseInt(response.getHeader("user_id")));
  }
  
  @DeleteMapping ("/{id}")
  public boolean deleteUserById(@PathVariable int id) {
    return userService.deleteById(id);
  }
}
