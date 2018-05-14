package com.buildboard.modules.signup.models.createcontractor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateContractorResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<CreateContractorDetail> datas = new ArrayList<>();
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

    public ArrayList<CreateContractorDetail> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<CreateContractorDetail> datas) {
        this.datas = datas;
    }
}
