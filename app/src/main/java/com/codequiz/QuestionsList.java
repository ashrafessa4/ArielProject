package com.codequiz;

import java.util.ArrayList;
import java.util.List;

public class QuestionsList {

    private String question, answer;
    private String userSelectedOption;
    private ArrayList options;

    public QuestionsList(String question, ArrayList options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;

        // assign userSelectedOption empty value by default
        this. userSelectedOption = "";
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList getOptions() { return options; }

    public String getAnswer() {
        return answer;
    }

    public String getUserSelectedOption() {
        return userSelectedOption;
    }

    public void setUserSelectedOption(String userSelectedOption) {
        this.userSelectedOption = userSelectedOption;
    }
}
