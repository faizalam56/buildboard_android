package com.buildboard.modules.home.modules.mailbox.inbox.models;

import com.google.gson.annotations.SerializedName;

public class DeleteMessageRequest {


    @SerializedName("recipient_id")
    String recipientid;

    public String getRecipientid() {
        return recipientid;
    }

    public void setRecipientid(String recipientid) {
        this.recipientid = recipientid;
    }
}