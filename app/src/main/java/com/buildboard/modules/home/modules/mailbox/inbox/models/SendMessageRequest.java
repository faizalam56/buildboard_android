package com.buildboard.modules.home.modules.mailbox.inbox.models;

import com.google.gson.annotations.SerializedName;

public class SendMessageRequest {


    @SerializedName("recipient_id")
    String recipientid;
    @SerializedName("type")
    String type;
    @SerializedName("body")
    String body;

    public String getRecipientid() {
        return recipientid;
    }

    public void setRecipientid(String recipientid) {
        this.recipientid = recipientid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
