package com.buildboard.modules.login.models.sociallogin;

import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {

    @SerializedName("provider") String provider;
    @SerializedName("provider_id") String providerId;

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
}
