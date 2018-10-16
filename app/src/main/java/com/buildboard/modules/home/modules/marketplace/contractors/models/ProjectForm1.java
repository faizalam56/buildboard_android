package com.buildboard.modules.home.modules.marketplace.contractors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by faiz on 24/8/18.
 */

public class ProjectForm1 {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("tasks")
    @Expose
    private List<Task1> tasks = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Task1> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task1> tasks) {
        this.tasks = tasks;
    }
}
