package com.example.questionnaire.controller;

import com.example.questionnaire.dto.QuestionList;
import com.example.questionnaire.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class Question {
    @Autowired
    private QuestionServiceImpl service;

    @GetMapping("/list")
    public QuestionList getQuestions() {
        return new QuestionList(service.getAll());
    }
}
