package com.example.language_practical2.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.language_practical2.model.dao.SkillsDao;
import com.example.language_practical2.model.dto.SkillsDto;

@Service
public class SkillsService {

  @Autowired
  SkillsDao skillsDao;

  public List<SkillsDto> getSkillsByUserId(int userId) throws SQLException {
    return skillsDao.getSkillsByUserId(userId);
  }

  public List<String> getSkills(int userId) throws SQLException {
    return skillsDao.getSkills(userId);
  }

  public List<SkillsDto> getAllSkills(String skill, String sort) throws SQLException {
    return skillsDao.getAllSkills(skill, sort);
  }

  public List<Map<String, Object>> getAllSkillsApi(String skill) throws SQLException {
    List<Map<String, Object>> list = new ArrayList<>();
    List<SkillsDto> skills = skillsDao.getAllSkills(skill, null);
    for (SkillsDto row : skills) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", row.getId().intValue());
      map.put("skill", row.getSkill());
      map.put("name", row.getName());
      list.add(map);
    }
    return list;
  }

  public void addSkill(int userId, String skill) throws SQLException {
    skillsDao.addSkill(userId, skill);
  }

  public int updateSkill(int id, String skill) throws SQLException {
    return skillsDao.updateSkill(id, skill);
  }

  public int deleteSkill(int id) throws SQLException {
    return skillsDao.deleteSkill(id);
  }
}