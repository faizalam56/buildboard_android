package com.buildboard.modules.signup.models.createconsumer;

import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateConsumerResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<CreateConsumerData> datas = new ArrayList<>();
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

    public ArrayList<CreateConsumerData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<CreateConsumerData> datas) {
        this.datas = datas;
    }
}
