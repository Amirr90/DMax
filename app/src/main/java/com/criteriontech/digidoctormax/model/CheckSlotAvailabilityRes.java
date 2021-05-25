package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckSlotAvailabilityRes {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseValue")
    @Expose
    private List<CheckSlotAvailabilityDataRes> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<CheckSlotAvailabilityDataRes> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<CheckSlotAvailabilityDataRes> responseValue) {
        this.responseValue = responseValue;
    }

}
