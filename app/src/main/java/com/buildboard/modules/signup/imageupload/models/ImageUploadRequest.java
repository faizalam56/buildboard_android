package com.buildboard.modules.signup.imageupload.models;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class ImageUploadRequest {
    @SerializedName("type") String type;
    @SerializedName("file_type") String fileType;
    @SerializedName("file[0]")
    MultipartBody.Part image;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public void setImage(MultipartBody.Part image) {
        this.image = image;
    }
}
