package com.buildboard.modules.signup.models.contractortype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ContractorTypeDetail implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    private String creationDate;
    @SerializedName("updated_at")
    private String lastChange;
    @SerializedName("type")
    private String type;

    protected ContractorTypeDetail(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        creationDate = in.readString();
        lastChange = in.readString();
    }

    public static final Creator<ContractorTypeDetail> CREATOR = new Creator<ContractorTypeDetail>() {
        @Override
        public ContractorTypeDetail createFromParcel(Parcel in) {
            return new ContractorTypeDetail(in);
        }

        @Override
        public ContractorTypeDetail[] newArray(int size) {
            return new ContractorTypeDetail[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(creationDate);
        dest.writeString(lastChange);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
