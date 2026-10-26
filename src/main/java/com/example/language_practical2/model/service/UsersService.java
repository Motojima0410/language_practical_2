package com.example.language_practical2.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.language_practical2.model.dao.UsersDao;
import com.example.language_practical2.model.dto.UsersDto;

@Service
public class UsersService {

  @Autowired
  UsersDao usersDao;

  public List<UsersDto> getUsers(String name) throws SQLException {
    return usersDao.getUsers(name);
  }

  public List<Map<String, Object>> getUsersApi(String name) throws SQLException {
    List<Map<String, Object>> users = new ArrayList<>();
    List<UsersDto> list = usersDao.getUsers(name);
    for (UsersDto user : list) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", user.getId().intValue());
      map.put("name", user.getName());
      users.add(map);
    }
    return users;
  }

  public void registerUser(String name) throws SQLException {
    usersDao.registerUser(name);
  }

  public int updateUser(int id, String name) throws SQLException {
    return usersDao.updateUser(id, name);
  }

  public int deleteUser(int id) throws SQLException {
    return usersDao.deleteUser(id);
  }
}