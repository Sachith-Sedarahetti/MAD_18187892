package com.example.guideapp;

import android.service.notification.StatusBarNotification;

public class UserMessage {
    String message;
    BaseMessage sender;
    //long createdAt;

    public UserMessage(String message, BaseMessage sender/*, long createdAt*/) {
        this.message = message;
        this.sender = sender;
        //this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseMessage getSender() {
        return sender;
    }

    public void setSender(BaseMessage sender) {
        this.sender = sender;
    }

    /*public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }*/
}
