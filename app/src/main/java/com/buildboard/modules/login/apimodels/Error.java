package com.buildboard.modules.login.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Error {

    @SerializedName("code")
    private String code = null;
    @SerializedName("code")
    private ArrayList<Object> message = new ArrayList<Object>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
