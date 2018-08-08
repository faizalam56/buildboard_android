package com.buildboard.modules.home.modules.mailbox.modules.models;

import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConsumerRelatedResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<ConsumerData> data = null;
    @SerializedName("error")
    @Expose
    private ErrorResponse error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ConsumerData> getData() {
        return data;
    }

    public void setData(ArrayList<ConsumerData> data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}
