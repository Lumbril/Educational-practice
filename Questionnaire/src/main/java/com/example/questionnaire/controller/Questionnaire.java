package com.example.questionnaire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questionnaire")
public class Questionnaire {
    @GetMapping("")
    public String getPage() {
        return "questionnaire";
    }
}
