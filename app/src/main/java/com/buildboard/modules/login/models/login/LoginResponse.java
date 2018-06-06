package com.buildboard.modules.login.models.login;

import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<LoginData> datas = new ArrayList<>();
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

    public ArrayList<LoginData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<LoginData> datas) {
        this.datas = datas;
    }
}