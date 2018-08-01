package com.buildboard.modules.home.modules.marketplace.contractors.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("question")
    private String question;
    @SerializedName("question_type")
    private String questionType;
    @SerializedName("question_choices")
    private List<String> questionChoices = null;
    @SerializedName("question_id")
    private String questionId;
    @SerializedName("answer")
    private List<String> answer = null;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<String> getQuestionChoices() {
        return questionChoices;
    }

    public void setQuestionChoices(List<String> questionChoices) {
        this.questionChoices = questionChoices;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

}