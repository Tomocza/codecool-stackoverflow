package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserLoginDTO;
import com.codecool.stackoverflowtw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("users")
public class UserController {
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
  public UserDTO loginUser(@RequestBody UserLoginDTO user) {
    Optional<UserDTO> userDTO = userService.login(user);
    return userDTO.orElse(null);
  }
  
  @DeleteMapping ("/{id}")
  public boolean deleteUserById(@PathVariable int id) {
    return userService.deleteById(id);
  }
}
