package com.buildboard.modules.home.modules.projects.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Task {
    @SerializedName("task")
    @Expose
    private String task;
    @SerializedName("questions")
    @Expose
    private List<ProjectTypeQuestion> questions = null;
    @SerializedName("task_id")
    @Expose
    private String taskId;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<ProjectTypeQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ProjectTypeQuestion> questions) {
        this.questions = questions;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
