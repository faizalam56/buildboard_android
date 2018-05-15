package com.buildboard.modules.home.modules.marketplace.models;

import com.google.gson.annotations.SerializedName;

public class ContractorInfo {

    @SerializedName("id")
    private String id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_no")
    private Object phoneNo;
    @SerializedName("type_of_contractor_id")
    private String typeOfContractorId;
    @SerializedName("business_name")
    private String businessName;
    @SerializedName("business_address")
    private String businessAddress;
    @SerializedName("working_area_radius")
    private Integer workingAreaRadius;
    @SerializedName("summary")
    private Object summary;
    @SerializedName("company")
    private String company;
    @SerializedName("image")
    private String image;
    @SerializedName("document1")
    private String document1;
    @SerializedName("document2")
    private String document2;
    @SerializedName("document3")
    private String document3;
    @SerializedName("document4")
    private String document4;
    @SerializedName("document5")
    private String document5;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Object getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Object phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTypeOfContractorId() {
        return typeOfContractorId;
    }

    public void setTypeOfContractorId(String typeOfContractorId) {
        this.typeOfContractorId = typeOfContractorId;
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

    public Integer getWorkingAreaRadius() {
        return workingAreaRadius;
    }

    public void setWorkingAreaRadius(Integer workingAreaRadius) {
        this.workingAreaRadius = workingAreaRadius;
    }

    public Object getSummary() {
        return summary;
    }

    public void setSummary(Object summary) {
        this.summary = summary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDocument1() {
        return document1;
    }

    public void setDocument1(String document1) {
        this.document1 = document1;
    }

    public String getDocument2() {
        return document2;
    }

    public void setDocument2(String document2) {
        this.document2 = document2;
    }

    public String getDocument3() {
        return document3;
    }

    public void setDocument3(String document3) {
        this.document3 = document3;
    }

    public String getDocument4() {
        return document4;
    }

    public void setDocument4(String document4) {
        this.document4 = document4;
    }

    public String getDocument5() {
        return document5;
    }

    public void setDocument5(String document5) {
        this.document5 = document5;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
