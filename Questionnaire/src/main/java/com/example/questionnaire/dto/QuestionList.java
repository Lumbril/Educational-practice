package com.example.questionnaire.dto;

import com.example.questionnaire.entity.Question;

import java.util.List;

public class QuestionList {
    private List<Question> questions;

    public QuestionList(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
