package com.codecool.stackoverflowtw.dao.user;

import com.codecool.stackoverflowtw.controller.dto.user.NewUserDTO;
import com.codecool.stackoverflowtw.dao.connection.JdbcConnector;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDaoJdbc implements UsersDAO {
  private final JdbcConnector connector;
  
  public UsersDaoJdbc(JdbcConnector connector) {
    this.connector = connector;
  }
  
  @Override
  public List<UserModel> getAll() {
    List<UserModel> result = new ArrayList<>();
    String sql = "select u.id, u.username, u.registered_at from users u";
    
    try (Connection connection = connector.getConnection(); Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      
      while (resultSet.next()) {
        result.add(getUserFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public Optional<UserModel> getById(int id) {
    String sql = "select u.id, u.username, u.registered_at from users u where u.id = ?";
    
    try (Connection connection = connector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      
      if (resultSet.next()) {
        return Optional.of(getUserFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return Optional.empty();
  }
  
  @Override
  public int add(NewUserDTO newUserDTO) {
    int result = -1;
    String sql = "insert into users(username, pw_hash) values(?,?) returning(id)";
    
    try (Connection connection = connector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, newUserDTO.username());
      statement.setString(2, newUserDTO.password());
      ResultSet resultSet = statement.executeQuery();
      
      if (resultSet.next()) {
        result = resultSet.getInt("id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  @Override
  public boolean deleteById(int id) {
    boolean result = false;
    String sql = "delete from users where id = ?";
    
    try (Connection connection = connector.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      result = statement.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
    return result;
  }
  
  private UserModel getUserFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String username = resultSet.getString("username");
    String pwHash = resultSet.getString("pw_hash");
    LocalDateTime registeredAt = resultSet.getTimestamp("registered_at").toLocalDateTime();
    return new UserModel(id, username, pwHash, registeredAt);
  }
}
