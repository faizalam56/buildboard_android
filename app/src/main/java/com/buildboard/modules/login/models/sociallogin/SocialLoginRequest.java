package com.buildboard.modules.login.models.sociallogin;

import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {

    @SerializedName("provider") String provider;
    @SerializedName("provider_id") String providerId;
    @SerializedName("first_name") private String firstName;
    @SerializedName("last_name") private String lastName;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
