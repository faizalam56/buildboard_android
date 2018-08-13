package com.buildboard.modules.home.modules.projects.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectTypeQuestion {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("question_choices")
    @Expose
    private List<String> questionChoices = null;
    @SerializedName("question_id")
    @Expose
    private String questionId;

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
}
