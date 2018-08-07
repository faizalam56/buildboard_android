
package com.buildboard.modules.home.modules.mailbox.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("participant1")
    @Expose
    private String participant1;
    @SerializedName("participant2")
    @Expose
    private String participant2;
    @SerializedName("last_message")
    @Expose
    private LastMessage lastMessage;
    @SerializedName("receiver")
    @Expose
    private Receiver receiver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
