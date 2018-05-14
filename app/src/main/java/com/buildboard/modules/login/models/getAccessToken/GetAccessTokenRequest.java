package com.buildboard.modules.login.models.getAccessToken;

import com.buildboard.constants.AppConfiguration;
import com.google.gson.annotations.SerializedName;

public class GetAccessTokenRequest implements AppConfiguration {

    @SerializedName("email")
    private String email = EMAIL;
    @SerializedName("password")
    private String password = PASSWORD;

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