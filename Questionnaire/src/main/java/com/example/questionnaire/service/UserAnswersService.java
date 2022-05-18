package com.example.questionnaire.service;

import com.example.questionnaire.entity.UserAnswer;

import java.util.List;

public interface UserAnswersService {
    void addUserAnswer(UserAnswer userAnswer) throws Exception;
    boolean checkUser(String login);
    List<UserAnswer> getUserAnswersByUserLogin(String login) throws Exception;
}
