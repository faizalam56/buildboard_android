package com.buildboard.modules.signup.contractor.previouswork.models;

import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SaveContractorImageResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<String> datas = new ArrayList<>();
    @SerializedName("error")
    private ErrorResponse ErrorObject;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorResponse getErrorObject() {
        return ErrorObject;
    }

    public void setErrorObject(ErrorResponse errorObject) {
        ErrorObject = errorObject;
    }

    public ArrayList<String> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<String> datas) {
        this.datas = datas;
    }
}
