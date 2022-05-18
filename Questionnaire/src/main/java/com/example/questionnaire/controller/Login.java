package com.example.questionnaire.controller;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Login {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "/")
    public String getLoginPage() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return "redirect:/questionnaire";
        }

        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(name = "login") String login,
                        @RequestParam(name = "password") String password,
                        HttpServletRequest request) {

        User user = userService.getByLogin(login);

        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            authUser(request, login, password);

            return "redirect:/questionnaire";
        }

        return "redirect:/";
    }

    private void authUser(HttpServletRequest request, String login, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
