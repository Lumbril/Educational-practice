package com.example.questionnaire.controller;

import com.example.questionnaire.dto.Answer;
import com.example.questionnaire.dto.QuestionList;
import com.example.questionnaire.entity.AnswerOption;
import com.example.questionnaire.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/question")
public class Question {
    @Autowired
    private QuestionServiceImpl service;

    @GetMapping("/list")
    public QuestionList getQuestions() {
        return new QuestionList(service.getAll());
    }

    @PostMapping(value = "/answer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity giveAnswer(@Valid @RequestBody Answer answer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not valid data");
        }

        com.example.questionnaire.entity.Question question = service.getQuestion(answer.getQuestionId());

        if (question == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no question with this id");
        }

        Set<AnswerOption> answerOptions = question.getAnswerOptions();

        boolean f = false;

        for (AnswerOption answerOption: answerOptions) {
            if (answerOption.getId().equals(answer.getAnswerId())) {
                f = true;

                break;
            }
        }

        if (!f) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no such answer");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Successful");
    }
}
