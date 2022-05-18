package com.example.questionnaire.controller;

import com.example.questionnaire.service.impl.UserAnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questionnaire")
public class Questionnaire {
    @Autowired
    private UserAnswerServiceImpl userAnswerService;
    
    @GetMapping("")
    public String getPage() {
        if (userAnswerService.checkUser(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return "redirect:/test/user";
        }
        
        return "questionnaire";
    }
}
