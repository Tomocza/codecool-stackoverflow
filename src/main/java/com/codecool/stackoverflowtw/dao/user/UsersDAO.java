package com.codecool.stackoverflowtw.dao.user;

import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;

import java.util.List;
import java.util.Optional;

public interface UsersDAO {
  List<UserModel> getAll();
  Optional<UserModel> getById(int id);
  int add(NewUserDTO newUserDTO);
  boolean deleteById(int id);
  Optional<UserModel> getByName(String name);
}
