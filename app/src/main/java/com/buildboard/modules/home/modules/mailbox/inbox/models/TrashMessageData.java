package com.buildboard.modules.home.modules.mailbox.inbox.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrashMessageData {

    @SerializedName("trash_count")
    @Expose
    private Integer trashCount;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getTrashCount() {
        return trashCount;
    }

    public void setTrashCount(Integer trashCount) {
        this.trashCount = trashCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}