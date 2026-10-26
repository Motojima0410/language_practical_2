package com.example.language_practical2.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.language_practical2.model.dto.SkillsDto;

@Repository
public class SkillsDao {

  public List<SkillsDto> getSkillsByUserId(int userId) throws SQLException {
    List<SkillsDto> skills = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement(
        "select skills.id, skills.skill, users.name from skills join users on skills.user_id = users.id where skills.user_id = ? order by skills.id");
    statement.setInt(1, userId);

    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      skills.add(new SkillsDto(
          resultSet.getBigDecimal("id"),
          resultSet.getString("skill"),
          resultSet.getString("name")));
    }

    return skills;
  }

  public List<String> getSkills(int userId) throws SQLException {
    List<String> skills = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement(
        "select skill from skills where user_id = ?");
    statement.setInt(1, userId);

    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      skills.add(resultSet.getString("skill"));
    }

    return skills;
  }

  public List<SkillsDto> getAllSkills(String skill, String sort) throws SQLException {
    List<SkillsDto> skills = new ArrayList<>();

    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    String orderBy = "skills.id";
    if (sort != null && sort.equals("skill")) {
      orderBy = "skills.skill";
    }
    if (sort != null && sort.equals("name")) {
      orderBy = "users.name";
    }

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
      skills.add(new SkillsDto(
          resultSet.getBigDecimal("id"),
          resultSet.getString("skill"),
          resultSet.getString("name")));
    }

    return skills;
  }

  public void addSkill(int userId, String skill) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("INSERT INTO skills (user_id, skill) VALUES (?, ?)");
    statement.setInt(1, userId);
    statement.setString(2, skill);
    statement.executeUpdate();
  }

  public int updateSkill(int id, String skill) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("update skills set skill = ? where id = ?");
    statement.setString(1, skill);
    statement.setInt(2, id);

    return statement.executeUpdate();
  }

  public int deleteSkill(int id) throws SQLException {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "Motomoto0410");

    PreparedStatement statement = connection.prepareStatement("delete from skills where id = ?");
    statement.setInt(1, id);

    return statement.executeUpdate();
  }
}