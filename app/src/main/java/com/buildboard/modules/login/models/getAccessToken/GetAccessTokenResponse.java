package com.buildboard.modules.login.models.getAccessToken;

import com.buildboard.models.ErrorResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAccessTokenResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<TokenData> tokenDataObject = new ArrayList<>();
    @SerializedName("error")
    private ErrorResponse ErrorObject;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorResponse getError() {
        return ErrorObject;
    }

    public void setError(ErrorResponse errorObject) {
        this.ErrorObject = errorObject;
    }

    public ArrayList<TokenData> getTokenDataObject() {
        return tokenDataObject;
    }

    public void setTokenDataObject(ArrayList<TokenData> tokenDataObject) {
        this.tokenDataObject = tokenDataObject;
    }
}