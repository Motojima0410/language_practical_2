package com.example.language_practical2.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.language_practical2.model.dto.SkillsDto;
import com.example.language_practical2.model.service.SkillsService;

@Controller
public class SkillsController {

  @Autowired
  SkillsService skillsService;

  @GetMapping("/skills")
  public String getSkills(
      @RequestParam(value = "skill", required = false) String skill,
      @RequestParam(value = "sort", required = false) String sort,
      Model model) throws SQLException {
    List<SkillsDto> skills = skillsService.getAllSkills(skill, sort);
    model.addAttribute("skills", skills);
    model.addAttribute("skill", skill == null ? "" : skill);
    return "skills";
  }

  @PostMapping("/skills")
  public String addSkill(
      @RequestParam("userId") int userId,
      @RequestParam("skill") String skill) throws SQLException {
    skillsService.addSkill(userId, skill);
    return "redirect:/skills";
  }

  @PostMapping("/skills/update")
  public String updateSkill(
      @RequestParam("id") int id,
      @RequestParam("skill") String skill) throws SQLException {
    skillsService.updateSkill(id, skill);
    return "redirect:/skills";
  }

  @PostMapping("/skills/delete")
  public String deleteSkill(@RequestParam("id") int id) throws SQLException {
    skillsService.deleteSkill(id);
    return "redirect:/skills";
  }
}