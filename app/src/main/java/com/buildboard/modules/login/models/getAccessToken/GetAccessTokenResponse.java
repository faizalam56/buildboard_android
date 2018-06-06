package com.buildboard.modules.login.models.getAccessToken;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAccessTokenResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<TokenData> tokenDataObject = new ArrayList<>();
    @SerializedName("error")
    private Error ErrorObject;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Error getError() {
        return ErrorObject;
    }

    public void setError(Error errorObject) {
        this.ErrorObject = errorObject;
    }

    public ArrayList<TokenData> getTokenDataObject() {
        return tokenDataObject;
    }

    public void setTokenDataObject(ArrayList<TokenData> tokenDataObject) {
        this.tokenDataObject = tokenDataObject;
    }
}