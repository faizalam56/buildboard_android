package com.buildboard.modules.home.modules.marketplace.models;

import com.google.gson.annotations.SerializedName;

public class NearByContractor {

    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("n_token")
    private String nToken;
    @SerializedName("admin_access")
    private int adminAccess;
    @SerializedName("role")
    private String role;
    @SerializedName("contractor_info")
    private ContractorInfo contractorInfo;

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

    public String getNToken() {
        return nToken;
    }

    public void setNToken(String nToken) {
        this.nToken = nToken;
    }

    public int getAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(int adminAccess) {
        this.adminAccess = adminAccess;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ContractorInfo getContractorInfo() {
        return contractorInfo;
    }

    public void setContractorInfo(ContractorInfo contractorInfo) {
        this.contractorInfo = contractorInfo;
    }
}