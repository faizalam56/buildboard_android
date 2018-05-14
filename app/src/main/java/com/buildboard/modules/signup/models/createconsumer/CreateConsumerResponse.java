package com.buildboard.modules.signup.models.createconsumer;

import com.google.gson.annotations.SerializedName;

public class CreateConsumerResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private CreateConsumerData data;
    @SerializedName("error")
    private Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreateConsumerData getData() {
        return data;
    }

    public void setData(CreateConsumerData data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
