package com.example.language_practical2.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.language_practical2.model.dto.SkillsDto;
import com.example.language_practical2.model.dto.UsersDto;
import com.example.language_practical2.model.service.SkillsService;
import com.example.language_practical2.model.service.UsersService;

@Controller
public class UsersController {

  @Autowired
  UsersService usersService;

  @Autowired
  SkillsService skillsService;

  @GetMapping("/users")
  public String getUsers(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "userId", required = false) Integer userId,
      Model model) throws SQLException {
    List<UsersDto> users = usersService.getUsers(name);
    model.addAttribute("users", users);
    model.addAttribute("name", name == null ? "" : name);

    List<SkillsDto> selectedSkills = new ArrayList<>();
    if (userId != null) {
      selectedSkills = skillsService.getSkillsByUserId(userId);
      model.addAttribute("userId", userId);

      String selectedName = "";
      for (UsersDto user : users) {
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
    usersService.registerUser(name);
    return "redirect:/users";
  }

  @PostMapping("/users/update")
  public String updateUser(@RequestParam("id") int id, @RequestParam("name") String name) throws SQLException {
    usersService.updateUser(id, name);
    return "redirect:/users";
  }

  @PostMapping("/users/delete")
  public String deleteUser(@RequestParam("id") int id) throws SQLException {
    usersService.deleteUser(id);
    return "redirect:/users";
  }

  @PostMapping("/users/skill")
  public String addUserSkill(
      @RequestParam("userId") int userId,
      @RequestParam("skill") String skill) throws SQLException {
    skillsService.addSkill(userId, skill);
    return "redirect:/users?userId=" + userId;
  }

  @PostMapping("/users/skill/update")
  public String updateUserSkill(
      @RequestParam("id") int id,
      @RequestParam("skill") String skill,
      @RequestParam("userId") int userId) throws SQLException {
    skillsService.updateSkill(id, skill);
    return "redirect:/users?userId=" + userId;
  }

  @PostMapping("/users/skill/delete")
  public String deleteUserSkill(
      @RequestParam("id") int id,
      @RequestParam("userId") int userId) throws SQLException {
    skillsService.deleteSkill(id);
    return "redirect:/users?userId=" + userId;
  }
}