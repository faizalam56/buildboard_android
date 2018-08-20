package com.buildboard.modules.home.modules.projects.models;

import java.io.Serializable;

public class ProjectScheduleLocation implements Serializable {
    private String projectTitle;
    private String projectDescription;
    private String preferredStartDate;
    private String preferredEndDate;
    private String projectAddress;
    private String preferredContractor;
    private String projectContractorPriority;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getPreferredStartDate() {
        return preferredStartDate;
    }

    public void setPreferredStartDate(String preferredStartDate) {
        this.preferredStartDate = preferredStartDate;
    }

    public String getPreferredEndDate() {
        return preferredEndDate;
    }

    public void setPreferredEndDate(String preferredEndDate) {
        this.preferredEndDate = preferredEndDate;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getPreferredContractor() {
        return preferredContractor;
    }

    public void setPreferredContractor(String preferredContractor) {
        this.preferredContractor = preferredContractor;
    }

    public String getProjectContractorPriority() {
        return projectContractorPriority;
    }

    public void setProjectContractorPriority(String projectContractorPriority) {
        this.projectContractorPriority = projectContractorPriority;
    }
}
