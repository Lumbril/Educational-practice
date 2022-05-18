package com.example.questionnaire.dto;

import java.util.List;

public class AnswerList {
    private List<Answer> answers;

    public AnswerList() {

    }

    public AnswerList(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
