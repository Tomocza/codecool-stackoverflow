package com.codecool.stackoverflowtw.dao.connection;

import java.sql.Connection;

public interface JdbcConnector {
  Connection getConnection();
}
