package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.OnlineAppointmentValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineAppointmentResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<OnlineAppointmentValue> responseValue = null;

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

    public List<OnlineAppointmentValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<OnlineAppointmentValue> responseValue) {
        this.responseValue = responseValue;
    }
}
