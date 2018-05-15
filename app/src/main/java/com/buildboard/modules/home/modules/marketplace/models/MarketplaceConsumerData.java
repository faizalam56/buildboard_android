package com.buildboard.modules.home.modules.marketplace.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MarketplaceConsumerData {

    @SerializedName("trendingServices")
    private ArrayList<TrendingService> trendingServices = null;
    @SerializedName("nearByContractor")
    private ArrayList<NearByContractor> nearByContractor = null;
    @SerializedName("projectTypes")
    private ArrayList<ProjectType> projectTypes = null;

    public ArrayList<TrendingService> getTrendingServices() {
        return trendingServices;
    }

    public void setTrendingServices(ArrayList<TrendingService> trendingServices) {
        this.trendingServices = trendingServices;
    }

    public ArrayList<NearByContractor> getNearByContractor() {
        return nearByContractor;
    }

    public void setNearByContractor(ArrayList<NearByContractor> nearByContractor) {
        this.nearByContractor = nearByContractor;
    }

    public ArrayList<ProjectType> getProjectTypes() {
        return projectTypes;
    }

    public void setProjectTypes(ArrayList<ProjectType> projectTypes) {
        this.projectTypes = projectTypes;
    }

}
