package com.buildboard.modules.home.modules.marketplace.models;

import com.buildboard.modules.home.modules.marketplace.contractors.models.NewProject;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class MarketplaceContractorData {

    @SerializedName("trendingProjects")
    private ArrayList<TrendingService> trendingServices = new ArrayList<>();
    @SerializedName("nearByProject")
    private ArrayList<NearByProjects> nearByProjects = new ArrayList<>();
    @SerializedName("projectTypes")
    private ArrayList<ProjectType> projectTypes = new ArrayList<>();
    @SerializedName("newProjects")
    private ArrayList<NewProject> newProjects = new ArrayList<>();
    public ArrayList<TrendingService> getTrendingServices() {
        return trendingServices;
    }

    public void setTrendingServices(ArrayList<TrendingService> trendingServices) {
        this.trendingServices = trendingServices;
    }

    public ArrayList<ProjectType> getProjectTypes() {
        return projectTypes;
    }

    public void setProjectTypes(ArrayList<ProjectType> projectTypes) {
        this.projectTypes = projectTypes;
    }

    public ArrayList<NearByProjects> getNearByProjects() {
        return nearByProjects;
    }

    public void setNearByProjects(ArrayList<NearByProjects> nearByProjects) {
        this.nearByProjects = nearByProjects;
    }

    public ArrayList<NewProject> getNewProjects() {
        return newProjects;
    }

    public void setNewProjects(ArrayList<NewProject> newProjects) {
        this.newProjects = newProjects;
    }

}
