package com.buildboard.modules.signup.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContractorListResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<Datum> data = null;
    @SerializedName("error")
    private Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}