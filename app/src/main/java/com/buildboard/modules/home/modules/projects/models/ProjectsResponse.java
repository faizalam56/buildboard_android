package com.buildboard.modules.home.modules.projects.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProjectsResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<ProjectsData> data = new ArrayList<>();
    @SerializedName("error")
    private Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ProjectsData> getDatas() {
        return data;
    }

    public void setDatas(ArrayList<ProjectsData> data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
