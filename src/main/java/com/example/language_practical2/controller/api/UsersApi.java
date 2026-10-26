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

import com.example.language_practical2.model.service.UsersService;

@RestController
public class UsersApi {

  @Autowired
  UsersService usersService;

  @GetMapping("/api/users")
  public List<Map<String, Object>> getUsers(
      @RequestParam(value = "name", required = false) String name) throws SQLException {
    return usersService.getUsersApi(name);
  }

  @PostMapping("/api/users")
  public String registerUser(@RequestBody Map<String, String> body) throws SQLException {
    String name = body.get("name");
    usersService.registerUser(name);
    return "登録成功";
  }

  @DeleteMapping("/api/users/{id}")
  public String deleteUser(@PathVariable("id") int id) throws SQLException {
    int count = usersService.deleteUser(id);
    return count + "件削除しました";
  }

  @PutMapping("/api/users/{id}")
  public String updateUser(@PathVariable("id") int id, @RequestBody Map<String, String> body) throws SQLException {
    String name = body.get("name");
    int count = usersService.updateUser(id, name);
    return count + "件更新しました";
  }
}