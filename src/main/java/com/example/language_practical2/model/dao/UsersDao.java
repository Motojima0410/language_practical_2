package com.example.language_practical2.model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.language_practical2.model.dto.UsersDto;

@Repository
public class UsersDao {

  public List<UsersDto> getUsers(String name) throws SQLException {
    List<UsersDto> users = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement;
    if (name == null || name.trim().equals("")) {
      statement = connection.prepareStatement("SELECT id, name FROM users order by id");
    } else {
      statement = connection.prepareStatement("SELECT id, name FROM users where name ~ ? order by id");
      statement.setString(1, name);
    }

    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      users.add(new UsersDto(resultSet.getBigDecimal("id"), resultSet.getString("name")));
    }

    return users;
  }

  public void registerUser(String name) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name) VALUES (?)");
    statement.setString(1, name);
    statement.executeUpdate();
  }

  public int updateUser(int id, String name) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("update users set name = ? where id = ?");
    statement.setString(1, name);
    statement.setInt(2, id);

    return statement.executeUpdate();
  }

  public int deleteUser(int id) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement deleteSkillsStatement = connection.prepareStatement("delete from skills where user_id = ?");
    deleteSkillsStatement.setInt(1, id);
    deleteSkillsStatement.executeUpdate();

    PreparedStatement deleteUsersStatement = connection.prepareStatement("delete from users where id = ?");
    deleteUsersStatement.setInt(1, id);

    return deleteUsersStatement.executeUpdate();
  }
}