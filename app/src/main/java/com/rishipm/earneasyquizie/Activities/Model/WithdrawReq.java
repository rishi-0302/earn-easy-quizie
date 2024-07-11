package com.rishipm.earneasyquizie.Activities.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

public class WithdrawReq {
    private String userId;
    private String emailAddress;
    private String requestedBy;


    @ServerTimestamp
    private Timestamp createdAt;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public WithdrawReq(String userId, String emailAddress, String requestedBy) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.requestedBy = requestedBy;
    }

    public WithdrawReq() {
    }

}
