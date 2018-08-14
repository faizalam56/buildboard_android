package com.buildboard.modules.home.modules.profile.contractor.models;

import com.buildboard.models.ErrorResponse;
import com.buildboard.modules.signup.contractor.businessdocuments.models.BusinessDocuments;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetBusinessDocumentsResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<BusinessDocuments> businessDocuments = new ArrayList<>();
    @SerializedName("error")
    private ErrorResponse error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public ArrayList<BusinessDocuments> getBusinessDocuments() {
        return businessDocuments;
    }

    public void setBusinessDocuments(ArrayList<BusinessDocuments> businessDocuments) {
        this.businessDocuments = businessDocuments;
    }
}
