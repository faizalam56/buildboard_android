package com.buildboard.modules.home.modules.projects.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProjectFormDetails implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("form")
    @Expose
    private List<ProjectTypeForm> form = new ArrayList<>();
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("type")
    @Expose
    private String type;

    protected ProjectFormDetails(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        image = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectFormDetails> CREATOR = new Creator<ProjectFormDetails>() {
        @Override
        public ProjectFormDetails createFromParcel(Parcel in) {
            return new ProjectFormDetails(in);
        }

        @Override
        public ProjectFormDetails[] newArray(int size) {
            return new ProjectFormDetails[size];
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProjectTypeForm> getForm() {
        return form;
    }

    public void setForm(List<ProjectTypeForm> form) {
        this.form = form;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
