package com.codecool.stackoverflowtw.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PsqlConnector implements JdbcConnector {
  private final String host;
  private final String port;
  private final String dbName;
  private final String username;
  private final String password;
  
  public PsqlConnector(String host, String port, String dbName, String username, String password) {
    this.host = host;
    this.port = port;
    this.dbName = dbName;
    this.username = username;
    this.password = password;
  }
  
  @Override
  public Connection getConnection() {
    Connection conn = null;
    try {
      String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
      Properties props = new Properties();
      props.setProperty("user", username);
      props.setProperty("password", password);
      conn = DriverManager.getConnection(url, props);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }
}
