package com.buildboard.modules.signup.contractor.models.previouswork;

import com.google.gson.annotations.SerializedName;

public class TestimonialData {

    @SerializedName("type") String type;
    @SerializedName("key") String key;
    @SerializedName("value") String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
