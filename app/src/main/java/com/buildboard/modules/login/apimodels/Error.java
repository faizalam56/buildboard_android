package com.buildboard.modules.login.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Error {

    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private ArrayList<String> message = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}