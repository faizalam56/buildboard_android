package com.buildboard.modules.home.modules.marketplace.contractors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by faiz on 24/8/18.
 */

public class Task1 {
    @SerializedName("task")
    @Expose
    private String task;
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = null;
    @SerializedName("task_id")
    @Expose
    private String taskId;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
