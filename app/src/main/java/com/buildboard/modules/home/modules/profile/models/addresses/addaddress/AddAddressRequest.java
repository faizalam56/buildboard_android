package com.buildboard.modules.home.modules.profile.models.addresses.addaddress;

import com.google.gson.annotations.SerializedName;

public class AddAddressRequest {

    @SerializedName("address")
    String address;
    @SerializedName("latitude")
    String latitude;
    @SerializedName("longitude")
    String longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
