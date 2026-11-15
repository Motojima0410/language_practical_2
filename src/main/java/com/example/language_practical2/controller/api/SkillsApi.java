package com.example.language_practical2.controller.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.language_practical2.model.service.SkillsService;

@RestController
public class SkillsApi {

  @Autowired
  SkillsService skillsService;

  @PostMapping("/api/skills")
  public String addSkill(@RequestBody Map<String, String> body) throws SQLException {
    int userId = Integer.parseInt(body.get("userId"));
    String skill = body.get("skill");
    skillsService.addSkill(userId, skill);
    return "スキル登録成功";
  }

  @GetMapping("/api/skills")
  public List<String> getSkills(@RequestParam("userId") int userId) throws SQLException {
    return skillsService.getSkills(userId);
  }

  @GetMapping("/api/skills/all")
  public List<Map<String, Object>> getAllSkills(
      @RequestParam(value = "skill", required = false) String skill) throws SQLException {
    return skillsService.getAllSkillsApi(skill);
  }

  @DeleteMapping("/api/skills/{id}")
  public String deleteSkill(@PathVariable("id") int id) throws SQLException {
    int count = skillsService.deleteSkill(id);
    return count + "件削除しました";
  }

  @PutMapping("/api/skills/{id}")
  public String updateSkill(@PathVariable("id") int id, @RequestBody Map<String, String> body) throws SQLException {
    String skill = body.get("skill");
    int count = skillsService.updateSkill(id, skill);
    return count + "件更新しました";
  }
}