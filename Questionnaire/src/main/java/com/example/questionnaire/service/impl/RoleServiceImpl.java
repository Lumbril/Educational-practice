package com.example.questionnaire.service.impl;

import com.example.questionnaire.entity.Role;
import com.example.questionnaire.repository.RoleRepository;
import com.example.questionnaire.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role addRole(Role role) throws Exception {
        if (roleRepository.findByName(role.getName()) != null) {
            throw new Exception("this role already exists");
        }

        Role saveRole = roleRepository.save(role);

        return saveRole;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role editRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAll() {
        return (List<Role>) roleRepository.findAll();
    }
}
