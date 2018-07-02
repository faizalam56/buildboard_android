package com.buildboard.modules.signup.contractor.models.businessinfo;

import com.google.gson.annotations.SerializedName;

public class BusinessInfoRequest {
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("business_name")
    private String businessName;
    @SerializedName("business_address")
    private String businessAddress;
    @SerializedName("min_area_radius")
    private Integer minAreaRadius;
    @SerializedName("max_area_radius")
    private Integer maxAreaRadius;
    @SerializedName("summary")
    private String summary;
    @SerializedName("password")
    private String password;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("provider")
    private String provider;
    @SerializedName("provider_id")
    private String providerId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public Integer getMinAreaRadius() {
        return minAreaRadius;
    }

    public void setMinAreaRadius(Integer minAreaRadius) {
        this.minAreaRadius = minAreaRadius;
    }

    public Integer getMaxAreaRadius() {
        return maxAreaRadius;
    }

    public void setMaxAreaRadius(Integer maxAreaRadius) {
        this.maxAreaRadius = maxAreaRadius;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
