package com.buildboard.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ErrorResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("messages")
    private ArrayList<String> message = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

}