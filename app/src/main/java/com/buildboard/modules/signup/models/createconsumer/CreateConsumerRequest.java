package com.buildboard.modules.signup.models.createconsumer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateConsumerRequest implements Serializable {

    @SerializedName("first_name") private String firstName;
    @SerializedName("last_name") private String lastName;
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;
    @SerializedName("address") private String address;
    @SerializedName("phone_no") private String phoneNo;
    @SerializedName("contact_mode") private String contactMode;
    @SerializedName("image") private String image;
    @SerializedName("latitude") private String latitude;
    @SerializedName("longitude") private String longitude;
    @SerializedName("provider") private String provider;
    @SerializedName("provider_id") private String providerId;
    @SerializedName("_method") private String methodName;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactMode() {
        return contactMode;
    }

    public void setContactMode(String contactMode) {
        this.contactMode = contactMode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getMethodName(){
        return methodName;
    }
    public void setMethodName(String methodName){this.methodName= methodName;}
}
