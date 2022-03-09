package com.example.questionnaire.service;

import com.example.questionnaire.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role) throws Exception;
    void delete(long id);
    Role getByName(String name);
    Role editRole(Role role);
    List<Role> getAll();
}
