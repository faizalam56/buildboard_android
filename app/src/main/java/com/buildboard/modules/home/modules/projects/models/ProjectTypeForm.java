package com.buildboard.modules.home.modules.projects.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectTypeForm {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
