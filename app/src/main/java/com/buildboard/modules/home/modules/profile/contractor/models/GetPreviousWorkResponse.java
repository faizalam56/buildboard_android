package com.buildboard.modules.home.modules.profile.contractor.models;

import com.buildboard.models.ErrorResponse;
import com.buildboard.modules.signup.contractor.businessdocuments.models.BusinessDocuments;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorks;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetPreviousWorkResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<PreviousWorks> previousWorks = new ArrayList<>();
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

    public ArrayList<PreviousWorks> getPreviousWorks() {
        return previousWorks;
    }

    public void setPreviousWorks(ArrayList<PreviousWorks> previousWorks) {
        this.previousWorks = previousWorks;
    }
}
