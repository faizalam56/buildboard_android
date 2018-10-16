package com.buildboard.modules.home.modules.marketplace.contractors.models;

import com.buildboard.modules.home.modules.marketplace.models.Consumer;
import com.buildboard.modules.home.modules.marketplace.models.ProjectType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by faiz on 24/8/18.
 */

public class ProjectDetailsData1 {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("any_varified_contractor_in_area_can_quote")
    @Expose
    private Integer anyVarifiedContractorInAreaCanQuote;
    @SerializedName("only_pref_contractor_can_quote")
    @Expose
    private Integer onlyPrefContractorCanQuote;
    @SerializedName("preferred_contractors")
    @Expose
    private List<PreferredContractor> preferredContractors = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("project_form")
    @Expose
    private List<ProjectForm1> projectForm = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("project_photo")
    @Expose
    private List<String> projectPhoto = null;
    @SerializedName("additional_attachment")
    @Expose
    private List<String> additionalAttachment = null;
    @SerializedName("preffered_material_description")
    @Expose
    private String prefferedMaterialDescription;
    @SerializedName("preffered_material_attachment")
    @Expose
    private List<String> prefferedMaterialAttachment = null;
    @SerializedName("contractor")
    @Expose
    private Object contractor;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("reviews_count")
    @Expose
    private Integer reviewsCount;
    @SerializedName("quotes_count")
    @Expose
    private Integer quotesCount;
    @SerializedName("project_type")
    @Expose
    private ProjectType projectType;
    @SerializedName("change_requests")
    @Expose
    private List<Object> changeRequests = null;
    @SerializedName("quotes")
    @Expose
    private List<Object> quotes = null;
    @SerializedName("consumer")
    @Expose
    private Consumer consumer;

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

    public List<ProjectForm1> getProjectForm() {
        return projectForm;
    }

    public void setProjectForm(List<ProjectForm1> projectForm) {
        this.projectForm = projectForm;
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

    public String getPrefferedMaterialDescription() {
        return prefferedMaterialDescription;
    }

    public void setPrefferedMaterialDescription(String prefferedMaterialDescription) {
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

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public Integer getQuotesCount() {
        return quotesCount;
    }

    public void setQuotesCount(Integer quotesCount) {
        this.quotesCount = quotesCount;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public List<Object> getChangeRequests() {
        return changeRequests;
    }

    public void setChangeRequests(List<Object> changeRequests) {
        this.changeRequests = changeRequests;
    }

    public List<Object> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Object> quotes) {
        this.quotes = quotes;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
