package com.buildboard.modules.home.modules.marketplace.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NearByProjects implements Serializable
{

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
    @SerializedName("distance")
    private Double distance;
    @SerializedName("project_type")
    private ProjectType projectType;
    @SerializedName("consumer")
    private Consumer consumer;
    @SerializedName("quotes")
    private List<Quote> quotes = null;
    private final static long serialVersionUID = 2094634918167730568L;

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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

}