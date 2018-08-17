package com.buildboard.modules.home.modules.projects.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectTypeQuestion implements Parcelable {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("question_choices")
    @Expose
    private List<String> questionChoices;
    @SerializedName("question_id")
    @Expose
    private String questionId;


    protected ProjectTypeQuestion(Parcel in) {
        question = in.readString();
        questionType = in.readString();
        questionChoices = in.createStringArrayList();
        questionId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(questionType);
        dest.writeStringList(questionChoices);
        dest.writeString(questionId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectTypeQuestion> CREATOR = new Creator<ProjectTypeQuestion>() {
        @Override
        public ProjectTypeQuestion createFromParcel(Parcel in) {
            return new ProjectTypeQuestion(in);
        }

        @Override
        public ProjectTypeQuestion[] newArray(int size) {
            return new ProjectTypeQuestion[size];
        }
    };

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
