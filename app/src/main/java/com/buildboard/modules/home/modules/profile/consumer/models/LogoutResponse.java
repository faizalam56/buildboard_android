package com.buildboard.modules.home.modules.profile.consumer.models;


import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LogoutResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<String> datas = new ArrayList<>();
    @SerializedName("error")
    private ErrorResponse error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public ArrayList<String> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<String> datas) {
        this.datas = datas;
    }
}
