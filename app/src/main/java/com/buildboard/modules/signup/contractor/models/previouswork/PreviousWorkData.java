package com.buildboard.modules.signup.contractor.models.previouswork;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PreviousWorkData {

    @SerializedName("type") String type;
    @SerializedName("key") String key;
    @SerializedName("value")
    ArrayList<String> value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }
}