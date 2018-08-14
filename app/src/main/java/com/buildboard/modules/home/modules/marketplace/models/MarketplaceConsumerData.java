package com.buildboard.modules.home.modules.marketplace.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MarketplaceConsumerData implements Parcelable{

    @SerializedName("trendingContractors")
    private ArrayList<TrendingService> trendingServices = new ArrayList<>();
    @SerializedName("nearByContractor")
    private ArrayList<NearByContractor> nearByContractor = new ArrayList<>();
    @SerializedName("projectTypes")
    private ArrayList<ProjectType> projectTypes = new ArrayList<>();

    public MarketplaceConsumerData(Parcel in) {
        trendingServices = in.createTypedArrayList(TrendingService.CREATOR);
        projectTypes = in.createTypedArrayList(ProjectType.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(trendingServices);
        dest.writeTypedList(projectTypes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MarketplaceConsumerData> CREATOR = new Creator<MarketplaceConsumerData>() {
        @Override
        public MarketplaceConsumerData createFromParcel(Parcel in) {
            return new MarketplaceConsumerData(in);
        }

        @Override
        public MarketplaceConsumerData[] newArray(int size) {
            return new MarketplaceConsumerData[size];
        }
    };

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
