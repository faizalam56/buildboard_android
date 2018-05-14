package com.buildboard.modules.signup.apimodels.createcontractor;

import com.google.gson.annotations.SerializedName;

public class CreateContractorResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private CreateContractorDetail data;
    @SerializedName("error")
    private Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreateContractorDetail getData() {
        return data;
    }

    public void setData(CreateContractorDetail data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
