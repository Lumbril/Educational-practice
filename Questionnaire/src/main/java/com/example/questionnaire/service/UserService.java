package com.example.questionnaire.service;

import com.example.questionnaire.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user) throws Exception;
    User getByLogin(String login);
    User editUser(User user);
    List<User> getAll();
}
