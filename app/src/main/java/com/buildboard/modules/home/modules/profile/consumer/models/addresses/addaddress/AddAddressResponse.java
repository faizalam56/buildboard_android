
package com.buildboard.modules.home.modules.profile.consumer.models.addresses.addaddress;

import java.util.List;

import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAddressResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("error")
    @Expose
    private ErrorResponse error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

}
