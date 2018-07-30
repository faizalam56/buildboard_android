package com.buildboard.modules.signup.contractor.previouswork.models;

import com.google.gson.annotations.SerializedName;

public class PreviousWorkRequest {

    @SerializedName("previousWork")
    private PreviousWorks previousWorks;
    @SerializedName("id")
    private String id;

    public PreviousWorks getPreviousWorks() {
        return previousWorks;
    }

    public void setPreviousWorks(PreviousWorks previousWorks) {
        this.previousWorks = previousWorks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
