package com.example.questionnaire.controller.rest;

import com.example.questionnaire.dto.Answer;
import com.example.questionnaire.dto.AnswerList;
import com.example.questionnaire.dto.QuestionList;
import com.example.questionnaire.dto.RightAnswerNum;
import com.example.questionnaire.entity.AnswerOption;
import com.example.questionnaire.entity.UserAnswer;
import com.example.questionnaire.service.impl.QuestionServiceImpl;
import com.example.questionnaire.service.impl.UserAnswerServiceImpl;
import com.example.questionnaire.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/question")
public class Question {
    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserAnswerServiceImpl userAnswerService;

    @GetMapping("/list")
    public QuestionList getQuestions() {
        return new QuestionList(questionService.getAll());
    }

    @PostMapping(value = "/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity giveAnswers(@Valid @RequestBody AnswerList answerList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not valid data");
        }

        if (userAnswerService.checkUser(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already answered");
        }

        for (Answer answer : answerList.getAnswers()) {
            com.example.questionnaire.entity.Question question = questionService.getQuestion(answer.getQuestionId());

            if (question == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no question with this id");
            }

            Set<AnswerOption> answerOptions = question.getAnswerOptions();
            AnswerOption userAnswerOption = null;

            boolean f = false;

            for (AnswerOption answerOption: answerOptions) {
                if (answerOption.getId().equals(answer.getAnswerId())) {
                    f = true;
                    userAnswerOption = answerOption;

                    break;
                }
            }

            if (!f) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no such answer");
            }

            boolean isRight = question.getAnswer().getId().equals(answer.getAnswerId()) ? true : false;

            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setQuestion(question);
            userAnswer.setAnswerOption(userAnswerOption);
            userAnswer.setUser(userService.getByLogin(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ));
            userAnswer.setRight(isRight);

            try {
                userAnswerService.addUserAnswer(userAnswer);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Successful");
    }

    @GetMapping("/count_right_answer")
    public RightAnswerNum getRightAnswerCount() {
        List<UserAnswer> answers = null;

        try {
            answers = userAnswerService.getUserAnswersByUserLogin(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        int count = 0;

        for (UserAnswer answer : answers) {
            count += answer.getRight() ? 1 : 0;
        }

        return new RightAnswerNum(count);
    }
}
