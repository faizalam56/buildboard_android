package com.buildboard.modules.home.modules.marketplace.contractors.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewProject {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("any_varified_contractor_in_area_can_quote")
    private Integer anyVarifiedContractorInAreaCanQuote;
    @SerializedName("only_pref_contractor_can_quote")
    private Integer onlyPrefContractorCanQuote;
    @SerializedName("preferred_contractors")
    private List<PreferredContractor> preferredContractors = null;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;
    @SerializedName("status")
    private String status;
    @SerializedName("category")
    private String category;
    @SerializedName("latitude")
    private Float latitude;
    @SerializedName("longitude")
    private Float longitude;
    @SerializedName("address")
    private String address;
    @SerializedName("project_photo")
    private List<String> projectPhoto = null;
    @SerializedName("additional_attachment")
    private List<String> additionalAttachment = null;
    @SerializedName("preffered_material_description")
    private Object prefferedMaterialDescription;
    @SerializedName("preffered_material_attachment")
    private List<String> prefferedMaterialAttachment = null;
    @SerializedName("contractor")
    private Object contractor;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAnyVarifiedContractorInAreaCanQuote() {
        return anyVarifiedContractorInAreaCanQuote;
    }

    public void setAnyVarifiedContractorInAreaCanQuote(Integer anyVarifiedContractorInAreaCanQuote) {
        this.anyVarifiedContractorInAreaCanQuote = anyVarifiedContractorInAreaCanQuote;
    }

    public Integer getOnlyPrefContractorCanQuote() {
        return onlyPrefContractorCanQuote;
    }

    public void setOnlyPrefContractorCanQuote(Integer onlyPrefContractorCanQuote) {
        this.onlyPrefContractorCanQuote = onlyPrefContractorCanQuote;
    }

    public List<PreferredContractor> getPreferredContractors() {
        return preferredContractors;
    }

    public void setPreferredContractors(List<PreferredContractor> preferredContractors) {
        this.preferredContractors = preferredContractors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getProjectPhoto() {
        return projectPhoto;
    }

    public void setProjectPhoto(List<String> projectPhoto) {
        this.projectPhoto = projectPhoto;
    }

    public List<String> getAdditionalAttachment() {
        return additionalAttachment;
    }

    public void setAdditionalAttachment(List<String> additionalAttachment) {
        this.additionalAttachment = additionalAttachment;
    }

    public Object getPrefferedMaterialDescription() {
        return prefferedMaterialDescription;
    }

    public void setPrefferedMaterialDescription(Object prefferedMaterialDescription) {
        this.prefferedMaterialDescription = prefferedMaterialDescription;
    }

    public List<String> getPrefferedMaterialAttachment() {
        return prefferedMaterialAttachment;
    }

    public void setPrefferedMaterialAttachment(List<String> prefferedMaterialAttachment) {
        this.prefferedMaterialAttachment = prefferedMaterialAttachment;
    }

    public Object getContractor() {
        return contractor;
    }

    public void setContractor(Object contractor) {
        this.contractor = contractor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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