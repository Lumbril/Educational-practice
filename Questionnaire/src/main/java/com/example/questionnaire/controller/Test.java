package com.example.questionnaire.controller;

import com.example.questionnaire.entity.Role;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.impl.RoleServiceImpl;
import com.example.questionnaire.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class Test {
    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/")
    public String test() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (user.getLogin() == null || user.getLogin().length() < 5) {
            model.addAttribute("error", "Длина логина должна быть не меньше 5");

            return "registration";
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("error", "Пароли не совпадают");

            return "registration";
        }

        try {
            String login = user.getLogin();
            String password = user.getPassword();

            userService.addUser(user);

            authUser(request, login, password);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());

            return "registration";
        }

        return "redirect:/test/user/";
    }

    private void authUser(HttpServletRequest request, String login, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
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
