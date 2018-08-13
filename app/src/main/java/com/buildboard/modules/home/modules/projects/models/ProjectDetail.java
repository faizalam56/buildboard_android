package com.buildboard.modules.home.modules.projects.models;

import com.buildboard.modules.signup.models.createconsumer.ConsumerInfo;
import com.google.gson.annotations.SerializedName;

public class ProjectDetail {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("any_varified_contractor_in_area_can_quote")
    private Integer anyVarifiedContractorInAreaCanQuote;
    @SerializedName("only_pref_contractor_can_quote")
    private Integer onlyPrefContractorCanQuote;
    @SerializedName("status")
    private String status;
    @SerializedName("category")
    private String category;
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
    @SerializedName("image")
    private String image;
    @SerializedName("quotes_count")
    private String quotesCount;
    @SerializedName("views_count")
    private String viewsCount;
    @SerializedName("preferred_contractors")
    private Object preferredContractors;
    @SerializedName("project_type")
    private ProjectType projectType;
    @SerializedName("consumer")
    private ConsumerInfo consumerInfo;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getPreferredContractors() {
        return preferredContractors;
    }

    public void setPreferredContractors(Object preferredContractors) {
        this.preferredContractors = preferredContractors;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
    public ConsumerInfo getConsumerInfo() {
        return consumerInfo;
    }

    public void setConsumerInfo(ConsumerInfo consumerInfo) {
        this.consumerInfo = consumerInfo;
    }

    public String getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(String viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getQuotesCount() {
        return quotesCount;
    }

    public void setQuotesCount(String quotesCount) {
        this.quotesCount = quotesCount;
    }
}
