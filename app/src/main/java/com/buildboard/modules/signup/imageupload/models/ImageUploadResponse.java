
package com.buildboard.modules.signup.imageupload.models;

import java.lang.Error;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUploadResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<String> data = null;
    @SerializedName("error")
    @Expose
    private java.lang.Error error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public java.lang.Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}
