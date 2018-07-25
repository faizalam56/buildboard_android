package com.buildboard.modules.signup.contractor.businessdocuments.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class BusinessDocuments {

    @SerializedName("Workman's Comp Insurance")
    private HashMap<Integer, ArrayList<DocumentData>> workmanCampInsurance;

    @SerializedName("Insurance")
    private HashMap<Integer, ArrayList<DocumentData>> insurance;

    @SerializedName("Certification")
    private HashMap<Integer, ArrayList<DocumentData>> certification;

    @SerializedName("Bonding")
    private HashMap<Integer, ArrayList<DocumentData>> bonding;

    @SerializedName("Business Licensing")
    private HashMap<Integer, ArrayList<DocumentData>> businessLicensing;

    public HashMap<Integer, ArrayList<DocumentData>> getWorkmanCampInsurance() {
        return workmanCampInsurance;
    }

    public void setWorkmanCampInsurance(HashMap<Integer, ArrayList<DocumentData>> workmanCampInsurance) {
        this.workmanCampInsurance = workmanCampInsurance;
    }

    public HashMap<Integer, ArrayList<DocumentData>> getInsurance() {
        return insurance;
    }

    public void setInsurance(HashMap<Integer, ArrayList<DocumentData>> insurance) {
        this.insurance = insurance;
    }

    public HashMap<Integer, ArrayList<DocumentData>> getCertification() {
        return certification;
    }

    public void setCertification(HashMap<Integer, ArrayList<DocumentData>> certification) {
        this.certification = certification;
    }

    public HashMap<Integer, ArrayList<DocumentData>> getBonding() {
        return bonding;
    }

    public void setBonding(HashMap<Integer, ArrayList<DocumentData>> bonding) {
        this.bonding = bonding;
    }

    public HashMap<Integer, ArrayList<DocumentData>> getBusinessLicensing() {
        return businessLicensing;
    }

    public void setBusinessLicensing(HashMap<Integer, ArrayList<DocumentData>> businessLicensing) {
        this.businessLicensing = businessLicensing;
    }
}
