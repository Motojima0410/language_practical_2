package com.example.language_practical2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class Example {

  @GetMapping("/api/users")
  public List<java.util.Map<String, Object>> getUsers() throws SQLException {
    List<java.util.Map<String, Object>> users = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres",  "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM users order by id");
    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {
      java.util.Map<String, Object> user = new java.util.HashMap<>();
      user.put("id", resultSet.getInt("id"));
      user.put("name", resultSet.getString("name"));
      users.add(user);
    }

    return users;
  }

  @PostMapping("/api/users")
  public String registerUser(@RequestBody java.util.Map<String, String> body) throws SQLException {
    String name = body.get("name");

    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection
        .prepareStatement("INSERT INTO users (name) VALUES (?)");
    statement.setString(1, name);
    statement.executeUpdate();

    return "登録成功";
  }

  @DeleteMapping("/api/users/{id}")
  public String deleteUser(@PathVariable("id") int id) throws SQLException {
    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410"
    );

    PreparedStatement deleteSkillsStatement = connection.prepareStatement(
      "delete from skills where user_id = ?"
    );
    deleteSkillsStatement.setInt(1, id);
    deleteSkillsStatement.executeUpdate();

    PreparedStatement deleteUsersStatement = connection.prepareStatement(
      "delete from users where id = ?"
    );
    deleteUsersStatement.setInt(1, id);

    int count = deleteUsersStatement.executeUpdate();

    return count + "件削除しました";
  }

  @PutMapping("/api/users/{id}")
  public String updateUser(@PathVariable("id") int id, @RequestBody java.util.Map<String, String> body) throws SQLException {
    String name = body.get("name");

    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410"
    );

    PreparedStatement statement = connection.prepareStatement(
      "update users set name = ? where id = ?"
    );
    statement.setString(1, name);
    statement.setInt(2, id);

    int count = statement.executeUpdate();

    return count + "件更新しました";
  }


  @PostMapping("/api/skills")
  public String addSkill(@RequestBody java.util.Map<String, String> body) throws SQLException {
    int userId = Integer.parseInt(body.get("userId"));
    String skill = body.get("skill");

    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("INSERT INTO skills (user_id, skill) VALUES (?, ?)");
    statement.setInt(1, userId);
    statement.setString(2, skill);
    statement.executeUpdate();

    return "スキル登録成功";
  }

  @GetMapping("/api/skills")
  public List<String> getSkills(@RequestParam("userId") int userId) throws SQLException {
    List<String> skills = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410"
    );

    PreparedStatement statement = connection.prepareStatement(
      "select skill from skills where user_id = ?"
    );
    statement.setInt(1, userId);

    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {
      skills.add(resultSet.getString("skill"));
    }

    return skills;
  }

  @GetMapping("/api/skills/all")
  public List<java.util.Map<String, Object>> getAllSkills() throws SQLException {
    List<java.util.Map<String, Object>> list = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410"
    );

    PreparedStatement statement = connection.prepareStatement(
      "select skills.id, skills.skill, users.name from skills join users on skills.user_id = users.id order by skills.id"
    );

    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {
      java.util.Map<String, Object> row = new java.util.HashMap<>();
      row.put("id", resultSet.getInt("id"));
      row.put("skill", resultSet.getString("skill"));
      row.put("name", resultSet.getString("name"));
      list.add(row);
    }

    return list;
  }

  @DeleteMapping("/api/skills/{id}")
  public String deleteSkill(@PathVariable("id") int id) throws SQLException {
    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410"
    );

    PreparedStatement statement = connection.prepareStatement(
      "delete from skills where id = ?"
    );
    statement.setInt(1, id);

    int count = statement.executeUpdate();

    return count + "件削除しました";

  }

  @PutMapping("/api/skills/{id}")
  public String updateSkill(@PathVariable("id") int id, @RequestBody java.util.Map<String, String> body) throws SQLException {
    String skill = body.get("skill");

    Connection connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410"
    );

    PreparedStatement statement = connection.prepareStatement(
      "update skills set skill = ? where id = ?"
    );
    statement.setString(1, skill);
    statement.setInt(2, id);

    int count = statement.executeUpdate();

    return count + "件更新しました";
  }

}