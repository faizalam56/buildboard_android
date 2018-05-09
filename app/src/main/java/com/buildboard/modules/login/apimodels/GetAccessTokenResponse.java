package com.buildboard.modules.login.apimodels;

import com.google.gson.annotations.SerializedName;

public class GetAccessTokenResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private TokenData tokenDataObject;
    @SerializedName("error")
    private Error ErrorObject;

    public String getStatus() {
        return status;
    }

    public TokenData getData() {
        return tokenDataObject;
    }

    public Error getError() {
        return ErrorObject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(TokenData tokenDataObject) {
        this.tokenDataObject = tokenDataObject;
    }

    public void setError(Error errorObject) {
        this.ErrorObject = errorObject;
    }
}