package com.buildboard.modules.home.modules.projects.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Task implements Parcelable{
    @SerializedName("task")
    @Expose
    private String task;
    @SerializedName("questions")
    @Expose
    private List<ProjectTypeQuestion> questions = new ArrayList<>();
    @SerializedName("task_id")
    @Expose
    private String taskId;

    protected Task(Parcel in) {
        task = in.readString();
        taskId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(task);
        dest.writeString(taskId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

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
