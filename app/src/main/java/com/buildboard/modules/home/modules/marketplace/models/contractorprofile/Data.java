
package com.buildboard.modules.home.modules.marketplace.models.contractorprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("admin_access")
    @Expose
    private Integer adminAccess;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("contractor_info")
    @Expose
    private ContractorInfo contractorInfo;
    @SerializedName("consumer_info")
    @Expose
    private Object consumerInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(Integer adminAccess) {
        this.adminAccess = adminAccess;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public ContractorInfo getContractorInfo() {
        return contractorInfo;
    }

    public void setContractorInfo(ContractorInfo contractorInfo) {
        this.contractorInfo = contractorInfo;
    }

    public Object getConsumerInfo() {
        return consumerInfo;
    }

    public void setConsumerInfo(Object consumerInfo) {
        this.consumerInfo = consumerInfo;
    }

}
