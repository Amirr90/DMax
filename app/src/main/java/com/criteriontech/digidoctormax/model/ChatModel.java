package com.criteriontech.digidoctormax.model;

import java.sql.Timestamp;

public class ChatModel {

    String timestamp;
    String appointmentId;
    String message;
    Boolean isSeen;
    String receiverId;
    String senderId;
    String serviceProviderTypeId;
    String createdDate;

    public ChatModel(String appointmentId, String message, String receiverId, String senderId, String serviceProviderTypeId) {
        this.appointmentId = appointmentId;
        this.message = message;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.serviceProviderTypeId = serviceProviderTypeId;
    }

    public ChatModel() {
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getServiceProviderTypeId() {
        return serviceProviderTypeId;
    }

    public void setServiceProviderTypeId(String serviceProviderTypeId) {
        this.serviceProviderTypeId = serviceProviderTypeId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getTimestamp() {
        Timestamp ts1 = Timestamp.valueOf(timestamp);
        return ts1.getTime();

    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSeen() {
        return isSeen;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    @Override
    public String toString() {
        return "{" +
                "timestamp=" + timestamp +
                ", appointmentId='" + appointmentId + '\'' +
                ", message='" + message + '\'' +
                ", isSeen=" + isSeen +
                ", receiverId='" + receiverId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", serviceProviderTypeId='" + serviceProviderTypeId + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
