package com.buildboard.modules.home.modules.marketplace.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceConsumerResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<MarketplaceConsumerData> datas = new ArrayList<>();
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

    public List<MarketplaceConsumerData> getDatas() {
        return datas;
    }

    public void setDatas(List<MarketplaceConsumerData> datas) {
        this.datas = datas;
    }
}
