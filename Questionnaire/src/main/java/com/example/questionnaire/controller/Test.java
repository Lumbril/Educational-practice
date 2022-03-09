package com.example.questionnaire.controller;

import com.example.questionnaire.entity.Role;
import com.example.questionnaire.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {
    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("/")
    public String test() {
        return "login";
    }

    @GetMapping("/add_role")
    @ResponseBody
    public String addRole() {
        Role role = new Role();
        role.setName("USER");

        try {
            roleService.addRole(role);
        } catch (Exception ex) {
            return "Failed";
        }

        return "Successful";
    }
}
