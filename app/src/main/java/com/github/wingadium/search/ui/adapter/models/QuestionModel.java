package com.github.wingadium.search.ui.adapter.models;

public class QuestionModel {

    private final String mQuestion;
    private final String mAnswer;

    public QuestionModel(String text, String answer) {
        mQuestion = text;
        mAnswer = answer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String getAnswer() {
        return mAnswer;
    }
}
