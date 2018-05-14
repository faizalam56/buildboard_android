package com.buildboard.modules.signup.models.createcontractor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CreateContractorRequest implements Parcelable {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("phone_no")
    private String phoneNo;
    @SerializedName("type_of_contractor_id")
    private String TypeOfContractorId;
    @SerializedName("business_name")
    private String businessName;
    @SerializedName("business_address")
    private String businessAddress;
    @SerializedName("working_area_radius")
    private String workingAreaRadius;
    @SerializedName("summary")
    private String summary;
    @SerializedName("company")
    private String company;

    public CreateContractorRequest() {}

    protected CreateContractorRequest(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        phoneNo = in.readString();
        TypeOfContractorId = in.readString();
        businessName = in.readString();
        businessAddress = in.readString();
        workingAreaRadius = in.readString();
        summary = in.readString();
        company = in.readString();
    }

    public static final Creator<CreateContractorRequest> CREATOR = new Creator<CreateContractorRequest>() {
        @Override
        public CreateContractorRequest createFromParcel(Parcel in) {
            return new CreateContractorRequest(in);
        }

        @Override
        public CreateContractorRequest[] newArray(int size) {
            return new CreateContractorRequest[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTypeOfContractorId() {
        return TypeOfContractorId;
    }

    public void setTypeOfContractorId(String typeOfContractorId) {
        TypeOfContractorId = typeOfContractorId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getWorkingAreaRadius() {
        return workingAreaRadius;
    }

    public void setWorkingAreaRadius(String workingAreaRadius) {
        this.workingAreaRadius = workingAreaRadius;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(phoneNo);
        dest.writeString(TypeOfContractorId);
        dest.writeString(businessName);
        dest.writeString(businessAddress);
        dest.writeString(workingAreaRadius);
        dest.writeString(summary);
        dest.writeString(company);
    }
}
