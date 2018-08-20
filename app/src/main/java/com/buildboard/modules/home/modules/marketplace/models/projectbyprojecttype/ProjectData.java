
package com.buildboard.modules.home.modules.marketplace.models.projectbyprojecttype;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectData {

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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("category")
    @Expose
    private String category;
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
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("project_type")
    @Expose
    private ProjectType projectType;
    @SerializedName("consumer")
    @Expose
    private Consumer consumer;
    @SerializedName("quotes")
    @Expose
    private List<Object> quotes = null;

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

    public List<Object> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Object> quotes) {
        this.quotes = quotes;
    }
}
