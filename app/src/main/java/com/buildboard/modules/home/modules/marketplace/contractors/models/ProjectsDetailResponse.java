package com.buildboard.modules.home.modules.marketplace.contractors.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectsDetailResponse implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<ProjectsDetailData> datas = new ArrayList<>();
    @SerializedName("error")
    private Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public ArrayList<ProjectsDetailData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ProjectsDetailData> datas) {
        this.datas = datas;
    }
}