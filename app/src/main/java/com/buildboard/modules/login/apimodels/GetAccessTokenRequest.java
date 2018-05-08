package com.buildboard.modules.login.apimodels;

import com.google.gson.annotations.SerializedName;

public class GetAccessTokenRequest {

    @SerializedName("email")
    private String email = "token@crownstack.com";
    @SerializedName("password")
    private String password = "password";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}