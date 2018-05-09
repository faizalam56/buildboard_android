package com.buildboard.modules.login.apimodels;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("refreshToken")
    private String refreshToken;
    @SerializedName("expire")
    private float expire;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public float getExpire() {
        return expire;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpire(float expire) {
        this.expire = expire;
    }
}
