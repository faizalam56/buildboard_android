package com.buildboard.modules.home.modules.marketplace.contractors.models;

import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NearByProjectsResponse implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<NearByProjectData> datas = new ArrayList<>();
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

    public ArrayList<NearByProjectData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<NearByProjectData> datas) {
        this.datas = datas;
    }
}