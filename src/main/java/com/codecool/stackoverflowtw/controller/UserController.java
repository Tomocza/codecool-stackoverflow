package com.codecool.stackoverflowtw.controller;

import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserDTO;
import com.codecool.stackoverflowtw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public int addNewUser(@RequestBody NewUserDTO user) {
    return userService.add(user);
  }
  
  @DeleteMapping ("/{id}")
  public boolean deleteUserById(@PathVariable int id) {
    return userService.deleteById(id);
  }
}
