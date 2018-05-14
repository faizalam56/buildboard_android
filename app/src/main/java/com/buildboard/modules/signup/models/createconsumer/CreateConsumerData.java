package com.buildboard.modules.signup.models.createconsumer;

import com.google.gson.annotations.SerializedName;

public class CreateConsumerData {

    @SerializedName("id") private String id;
    @SerializedName("email") private String email;
    @SerializedName("n_token") private String nToken;
    @SerializedName("admin_access") private String adminAccess;
    @SerializedName("role") private String role;
    @SerializedName("message") private String message;
    @SerializedName("consumer_info") private ConsumerInfo consumerInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getnToken() {
        return nToken;
    }

    public void setnToken(String nToken) {
        this.nToken = nToken;
    }

    public String getAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(String adminAccess) {
        this.adminAccess = adminAccess;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ConsumerInfo getConsumerInfo() {
        return consumerInfo;
    }

    public void setConsumerInfo(ConsumerInfo consumerInfo) {
        this.consumerInfo = consumerInfo;
    }
}
