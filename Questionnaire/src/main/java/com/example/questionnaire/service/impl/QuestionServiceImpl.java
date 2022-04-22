package com.example.questionnaire.service.impl;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.repository.QuestionRepository;
import com.example.questionnaire.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question getQuestion(long id) {
        return questionRepository.findById(id).isEmpty() ? null : questionRepository.findById(id).get();
    }

    @Override
    public List<Question> getAll() {
        return (List<Question>) questionRepository.findAll();
    }
}
