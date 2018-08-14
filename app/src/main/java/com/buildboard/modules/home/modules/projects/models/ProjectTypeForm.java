package com.buildboard.modules.home.modules.projects.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProjectTypeForm implements Parcelable {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = new ArrayList<>();

    public ProjectTypeForm(Parcel in) {
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectTypeForm> CREATOR = new Creator<ProjectTypeForm>() {
        @Override
        public ProjectTypeForm createFromParcel(Parcel in) {
            return new ProjectTypeForm(in);
        }

        @Override
        public ProjectTypeForm[] newArray(int size) {
            return new ProjectTypeForm[size];
        }
    };

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
