package com.buildboard.modules.login.models.login;

import com.buildboard.constants.AppConfiguration;
import com.google.gson.annotations.SerializedName;

public class LoginRequest implements AppConfiguration {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

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