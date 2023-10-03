package com.codecool.stackoverflowtw.service;

import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;
import com.codecool.stackoverflowtw.controller.dto.user.UserDTO;
import com.codecool.stackoverflowtw.dao.user.UserModel;
import com.codecool.stackoverflowtw.dao.user.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UsersDAO usersDAO;
  
  @Autowired
  public UserService(UsersDAO usersDAO) {
    this.usersDAO = usersDAO;
  }
  
  public List<UserDTO> getAll() {
    return usersDAO.getAll().stream().map(e -> new UserDTO(e.id(), e.username(), e.registeredAt())).toList();
  }
  
  public Optional<UserDTO> getById(int id) {
    Optional<UserModel> result = usersDAO.getById(id);
    return result.map(userModel -> new UserDTO(userModel.id(), userModel.username(), userModel.registeredAt()));
  }
  
  public boolean deleteById(int id) {
    return usersDAO.deleteById(id);
  }
  
  public int add(NewUserDTO user) {
    String salt = BCrypt.gensalt(10, new SecureRandom());
    String password = BCrypt.hashpw(user.password(), salt);
    NewUserDTO newUser = new NewUserDTO(user.username(), password);
    return usersDAO.add(newUser);
  }
}