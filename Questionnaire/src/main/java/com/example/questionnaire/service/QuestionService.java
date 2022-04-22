package com.example.questionnaire.service;

import com.example.questionnaire.entity.Question;

import java.util.List;

public interface QuestionService {
    Question getQuestion(long id);
    List<Question> getAll();
}
