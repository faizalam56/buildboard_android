package com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContractorByProjectTypeResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<ContractorByProjectTypeData> datas = new ArrayList<>();
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

    public ArrayList<ContractorByProjectTypeData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ContractorByProjectTypeData> datas) {
        this.datas = datas;
    }
}
