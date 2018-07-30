package com.buildboard.modules.signup.contractor.previouswork.models;

import com.google.gson.annotations.SerializedName;

public class SaveContractorImageRequest {

    @SerializedName("image")
    private String imageUrl;
    @SerializedName("id")
    private String id;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
