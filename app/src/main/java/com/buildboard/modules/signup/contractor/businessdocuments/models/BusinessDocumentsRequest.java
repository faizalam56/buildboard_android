package com.buildboard.modules.signup.contractor.businessdocuments.models;

import com.google.gson.annotations.SerializedName;

public class BusinessDocumentsRequest {

    @SerializedName("businessDocuments")
    private BusinessDocuments businessDocuments;
    @SerializedName("id")
    private String id;

    public BusinessDocuments getBusinessDocuments() {
        return businessDocuments;
    }

    public void setBusinessDocuments(BusinessDocuments businessDocuments) {
        this.businessDocuments = businessDocuments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
