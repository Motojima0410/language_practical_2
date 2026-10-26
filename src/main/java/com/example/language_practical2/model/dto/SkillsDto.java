package com.example.language_practical2.model.dto;

import java.math.BigDecimal;

public class SkillsDto {
  private BigDecimal id;
  private String skill;
  private String name;

  public SkillsDto(BigDecimal id, String skill, String name) {
    this.id = id;
    this.skill = skill;
    this.name = name;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public BigDecimal getId() {
    return id;
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  public String getSkill() {
    return skill;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}