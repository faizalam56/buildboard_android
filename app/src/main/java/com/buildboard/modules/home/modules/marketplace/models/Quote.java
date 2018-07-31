package com.buildboard.modules.home.modules.marketplace.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Quote implements Serializable {

    private final static long serialVersionUID = -2070120511721318107L;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("project_scope")
    @Expose
    private Object projectScope;
    @SerializedName("specification")
    @Expose
    private String specification;
    @SerializedName("documents_and_attachmments")
    @Expose
    private Object documentsAndAttachmments;
    @SerializedName("permit")
    @Expose
    private Object permit;
    @SerializedName("estimated_start_date")
    @Expose
    private String estimatedStartDate;
    @SerializedName("estimated_end_date")
    @Expose
    private String estimatedEndDate;
    @SerializedName("job_time")
    @Expose
    private String jobTime;
    @SerializedName("labour_cost")
    @Expose
    private Integer labourCost;
    @SerializedName("material_cost")
    @Expose
    private Integer materialCost;
    @SerializedName("general_conditions_cost")
    @Expose
    private Integer generalConditionsCost;
    @SerializedName("total_cost")
    @Expose
    private Integer totalCost;
    @SerializedName("project_exclusions")
    @Expose
    private Object projectExclusions;
    @SerializedName("additional_notes")
    @Expose
    private Object additionalNotes;
    @SerializedName("contractor")
    @Expose
    private String contractor;
    @SerializedName("project")
    @Expose
    private String project;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getProjectScope() {
        return projectScope;
    }

    public void setProjectScope(Object projectScope) {
        this.projectScope = projectScope;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Object getDocumentsAndAttachmments() {
        return documentsAndAttachmments;
    }

    public void setDocumentsAndAttachmments(Object documentsAndAttachmments) {
        this.documentsAndAttachmments = documentsAndAttachmments;
    }

    public Object getPermit() {
        return permit;
    }

    public void setPermit(Object permit) {
        this.permit = permit;
    }

    public String getEstimatedStartDate() {
        return estimatedStartDate;
    }

    public void setEstimatedStartDate(String estimatedStartDate) {
        this.estimatedStartDate = estimatedStartDate;
    }

    public String getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(String estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    public Integer getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(Integer labourCost) {
        this.labourCost = labourCost;
    }

    public Integer getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(Integer materialCost) {
        this.materialCost = materialCost;
    }

    public Integer getGeneralConditionsCost() {
        return generalConditionsCost;
    }

    public void setGeneralConditionsCost(Integer generalConditionsCost) {
        this.generalConditionsCost = generalConditionsCost;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Object getProjectExclusions() {
        return projectExclusions;
    }

    public void setProjectExclusions(Object projectExclusions) {
        this.projectExclusions = projectExclusions;
    }

    public Object getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(Object additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}