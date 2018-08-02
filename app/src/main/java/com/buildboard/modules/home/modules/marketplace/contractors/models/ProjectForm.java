package com.buildboard.modules.home.modules.marketplace.contractors.models;

import java.util.List;

import com.buildboard.modules.home.modules.marketplace.contractors.models.Task;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectForm {

    @SerializedName("category")
    private String category;
    @SerializedName("tasks")
   // private List<Task> tasks = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

  /* public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }*/

}