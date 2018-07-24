package com.buildboard.modules.signup.contractor.models.businessdocument;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusinessDocumentsResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<String> data = new ArrayList<>();
    @SerializedName("error")
    private Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
