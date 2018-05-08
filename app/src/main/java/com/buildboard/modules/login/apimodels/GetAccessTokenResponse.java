package com.buildboard.modules.login.apimodels;

import com.google.gson.annotations.SerializedName;

public class GetAccessTokenResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Data DataObject;
    @SerializedName("error")
    private Error ErrorObject;

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return DataObject;
    }

    public Error getError() {
        return ErrorObject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(Data dataObject) {
        this.DataObject = dataObject;
    }

    public void setError(Error errorObject) {
        this.ErrorObject = errorObject;
    }
}