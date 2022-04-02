package com.example.questionnaire.controller;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.impl.RoleServiceImpl;
import com.example.questionnaire.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class Registration {
    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping()
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping()
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
}
