package com.buildboard.modules.home.modules.mailbox.inbox.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrashMessageResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<TrashMessageData> data = null;
    @SerializedName("error")
    @Expose
    private Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TrashMessageData> getData() {
        return data;
    }

    public void setData(List<TrashMessageData> data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}