package com.buildboard.modules.signup.models.contractortype;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkTypeRequest {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("project_type_id")
    @Expose
    private List<String> projectTypeId = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(List<String> projectTypeId) {
        this.projectTypeId = projectTypeId;
    }
}
