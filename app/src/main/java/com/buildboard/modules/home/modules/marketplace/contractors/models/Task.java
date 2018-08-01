package com.buildboard.modules.home.modules.marketplace.contractors.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("task")
    @Expose
    private String task;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("task_id")
    @Expose
    private String taskId;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}