package com.example.questionnaire.controller;

import com.example.questionnaire.entity.Role;
import com.example.questionnaire.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

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

    @RequestMapping(value = "/json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getRequestAttr(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String s;

        while ((s = request.getReader().readLine()) != null) {
            stringBuilder.append(s);
        }

        return stringBuilder.toString();
    }
}
