package com.example.questionnaire.service.impl;

import com.example.questionnaire.entity.UserAnswer;
import com.example.questionnaire.repository.UserAnswersRepository;
import com.example.questionnaire.service.UserAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAnswerServiceImpl implements UserAnswersService {
    @Autowired
    private UserAnswersRepository userAnswersRepository;

    @Override
    public void addUserAnswer(UserAnswer userAnswer) throws Exception {
        userAnswersRepository.save(userAnswer);
    }

    @Override
    public boolean checkUser(String login) {
        return userAnswersRepository.findByUserLogin(login).size() > 0 ? true : false;
    }

    @Override
    public List<UserAnswer> getUserAnswersByUserLogin(String login) throws Exception {
        return userAnswersRepository.findByUserLogin(login);
    }
}
