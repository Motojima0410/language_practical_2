package com.example.language_practical2;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleTemplates {

  @GetMapping("/users")
  public String getUsers(Model model) throws SQLException {
    List<Users> users = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM users order by id");
    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {
      BigDecimal id = resultSet.getBigDecimal("id");
      String name = resultSet.getString("name");
      users.add(new Users(id, name));
    }

    model.addAttribute("users", users);

    List<Skills> skills = new ArrayList<>();
    PreparedStatement skillStatement = connection.prepareStatement(
        "select skills.id, skills.skill, users.name from skills join users on skills.user_id = users.id order by skills.id");
    ResultSet skillResultSet = skillStatement.executeQuery();

    while (skillResultSet.next()) {
      BigDecimal skillId = skillResultSet.getBigDecimal("id");
      String skill = skillResultSet.getString("skill");
      String skillUserName = skillResultSet.getString("name");
      skills.add(new Skills(skillId, skill, skillUserName));
    }

    model.addAttribute("skills", skills);

    return "users";
  }

  @GetMapping("/skills")
  public String getSkills(Model model) throws SQLException {
    return getUsers(model);
  }
}