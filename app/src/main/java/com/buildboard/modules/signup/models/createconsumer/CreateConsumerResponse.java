package com.buildboard.modules.signup.models.createconsumer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateConsumerResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<CreateConsumerData> datas = new ArrayList<>();
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

    public ArrayList<CreateConsumerData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<CreateConsumerData> datas) {
        this.datas = datas;
    }
}
