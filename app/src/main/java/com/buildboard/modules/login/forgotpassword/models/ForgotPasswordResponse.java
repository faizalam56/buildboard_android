package com.buildboard.modules.login.forgotpassword.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForgotPasswordResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<String> datas = new ArrayList<>();
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

    public ArrayList<String> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<String> datas) {
        this.datas = datas;
    }
}
