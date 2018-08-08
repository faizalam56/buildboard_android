package com.buildboard.modules.signup.contractor.businessdocuments.models;

import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusinessDocumentsResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<String> data = new ArrayList<>();
    @SerializedName("error")
    private ErrorResponse error;

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

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}
