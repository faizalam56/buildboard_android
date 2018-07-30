package com.buildboard.modules.home.modules.marketplace.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MarketPlaceContractorResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<MarketplaceContractorData> datas = new ArrayList<>();
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

    public List<MarketplaceContractorData> getDatas() {
        return datas;
    }

    public void setDatas(List<MarketplaceContractorData> datas) {
        this.datas = datas;
    }
}
