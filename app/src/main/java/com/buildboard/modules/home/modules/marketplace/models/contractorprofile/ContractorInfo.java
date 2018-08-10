
package com.buildboard.modules.home.modules.marketplace.models.contractorprofile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContractorInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_address")
    @Expose
    private String businessAddress;
    @SerializedName("business_address1")
    @Expose
    private Object businessAddress1;
    @SerializedName("business_address2")
    @Expose
    private Object businessAddress2;
    @SerializedName("business_address3")
    @Expose
    private Object businessAddress3;
    @SerializedName("years_in_business")
    @Expose
    private Integer yearsInBusiness;
    @SerializedName("min_area_radius")
    @Expose
    private Integer minAreaRadius;
    @SerializedName("max_area_radius")
    @Expose
    private Integer maxAreaRadius;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("company")
    @Expose
    private Object company;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("verified")
    @Expose
    private Integer verified;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("project_type")
    @Expose
    private List<ProjectType> projectType = null;

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public Object getBusinessAddress1() {
        return businessAddress1;
    }

    public void setBusinessAddress1(Object businessAddress1) {
        this.businessAddress1 = businessAddress1;
    }

    public Object getBusinessAddress2() {
        return businessAddress2;
    }

    public void setBusinessAddress2(Object businessAddress2) {
        this.businessAddress2 = businessAddress2;
    }

    public Object getBusinessAddress3() {
        return businessAddress3;
    }

    public void setBusinessAddress3(Object businessAddress3) {
        this.businessAddress3 = businessAddress3;
    }

    public Integer getYearsInBusiness() {
        return yearsInBusiness;
    }

    public void setYearsInBusiness(Integer yearsInBusiness) {
        this.yearsInBusiness = yearsInBusiness;
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

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
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

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ProjectType> getProjectType() {
        return projectType;
    }

    public void setProjectType(List<ProjectType> projectType) {
        this.projectType = projectType;
    }

}
