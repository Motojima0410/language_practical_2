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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExampleTemplates {

  @GetMapping("/users")
  public String getUsers(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "userId", required = false) Integer userId,
      Model model) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    List<Users> users = new ArrayList<>();
    PreparedStatement statement;
    if (name == null || name.trim().equals("")) {
      statement = connection.prepareStatement("SELECT id, name FROM users order by id");
    } else {
      statement = connection.prepareStatement("SELECT id, name FROM users where name ~ ? order by id");
      statement.setString(1, name);
    }
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      users.add(new Users(resultSet.getBigDecimal("id"), resultSet.getString("name")));
    }
    model.addAttribute("users", users);
    model.addAttribute("name", name == null ? "" : name);

    List<Skills> selectedSkills = new ArrayList<>();
    if (userId != null) {
      PreparedStatement skillStatement = connection.prepareStatement(
          "select skills.id, skills.skill, users.name from skills join users on skills.user_id = users.id where skills.user_id = ? order by skills.id");
      skillStatement.setInt(1, userId);
      ResultSet skillResultSet = skillStatement.executeQuery();
      while (skillResultSet.next()) {
        selectedSkills.add(new Skills(
            skillResultSet.getBigDecimal("id"),
            skillResultSet.getString("skill"),
            skillResultSet.getString("name")));
      }
      model.addAttribute("userId", userId);

      String selectedName = "";
      for (Users user : users) {
        if (user.getId().intValue() == userId) {
          selectedName = user.getName();
        }
      }
      model.addAttribute("selectedName", selectedName);
    }
    model.addAttribute("selectedSkills", selectedSkills);

    return "users";
  }

  @PostMapping("/users")
  public String registerUser(@RequestParam("name") String name) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name) VALUES (?)");
    statement.setString(1, name);
    statement.executeUpdate();
    return "redirect:/users";
  }

  @PostMapping("/users/update")
  public String updateUser(@RequestParam("id") int id, @RequestParam("name") String name) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("update users set name = ? where id = ?");
    statement.setString(1, name);
    statement.setInt(2, id);
    statement.executeUpdate();
    return "redirect:/users";
  }

  @PostMapping("/users/delete")
  public String deleteUser(@RequestParam("id") int id) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement deleteSkillsStatement = connection.prepareStatement("delete from skills where user_id = ?");
    deleteSkillsStatement.setInt(1, id);
    deleteSkillsStatement.executeUpdate();
    PreparedStatement deleteUsersStatement = connection.prepareStatement("delete from users where id = ?");
    deleteUsersStatement.setInt(1, id);
    deleteUsersStatement.executeUpdate();
    return "redirect:/users";
  }

  @PostMapping("/users/skill")
  public String addUserSkill(
      @RequestParam("userId") int userId,
      @RequestParam("skill") String skill) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("INSERT INTO skills (user_id, skill) VALUES (?, ?)");
    statement.setInt(1, userId);
    statement.setString(2, skill);
    statement.executeUpdate();
    return "redirect:/users?userId=" + userId;
  }

  @PostMapping("/users/skill/update")
  public String updateUserSkill(
      @RequestParam("id") int id,
      @RequestParam("skill") String skill,
      @RequestParam("userId") int userId) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("update skills set skill = ? where id = ?");
    statement.setString(1, skill);
    statement.setInt(2, id);
    statement.executeUpdate();
    return "redirect:/users?userId=" + userId;
  }

  @PostMapping("/users/skill/delete")
  public String deleteUserSkill(
      @RequestParam("id") int id,
      @RequestParam("userId") int userId) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("delete from skills where id = ?");
    statement.setInt(1, id);
    statement.executeUpdate();
    return "redirect:/users?userId=" + userId;
  }

  @GetMapping("/skills")
  public String getSkills(
      @RequestParam(value = "skill", required = false) String skill,
      @RequestParam(value = "sort", required = false) String sort,
      Model model) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    String orderBy = "skills.id";
    if (sort != null && sort.equals("skill")) {
      orderBy = "skills.skill";
    }
    if (sort != null && sort.equals("name")) {
      orderBy = "users.name";
    }

    List<Skills> skills = new ArrayList<>();
    PreparedStatement statement;
    if (skill == null || skill.trim().equals("")) {
      statement = connection.prepareStatement(
          "select skills.id, skills.skill, users.name from skills join users on skills.user_id = users.id order by " + orderBy);
    } else {
      statement = connection.prepareStatement(
          "select skills.id, skills.skill, users.name from skills join users on skills.user_id = users.id where skills.skill ~ ? order by " + orderBy);
      statement.setString(1, skill);
    }
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      skills.add(new Skills(
          resultSet.getBigDecimal("id"),
          resultSet.getString("skill"),
          resultSet.getString("name")));
    }
    model.addAttribute("skills", skills);
    model.addAttribute("skill", skill == null ? "" : skill);
    return "skills";
  }

  @PostMapping("/skills")
  public String addSkill(
      @RequestParam("userId") int userId,
      @RequestParam("skill") String skill) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("INSERT INTO skills (user_id, skill) VALUES (?, ?)");
    statement.setInt(1, userId);
    statement.setString(2, skill);
    statement.executeUpdate();
    return "redirect:/skills";
  }

  @PostMapping("/skills/update")
  public String updateSkill(
      @RequestParam("id") int id,
      @RequestParam("skill") String skill) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("update skills set skill = ? where id = ?");
    statement.setString(1, skill);
    statement.setInt(2, id);
    statement.executeUpdate();
    return "redirect:/skills";
  }

  @PostMapping("/skills/delete")
  public String deleteSkill(@RequestParam("id") int id) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");
    PreparedStatement statement = connection.prepareStatement("delete from skills where id = ?");
    statement.setInt(1, id);
    statement.executeUpdate();
    return "redirect:/skills";
  }
}