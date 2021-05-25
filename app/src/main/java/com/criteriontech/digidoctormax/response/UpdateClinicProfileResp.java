package com.criteriontech.digidoctormax.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateClinicProfileResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public Object responseValue;

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

    public Object getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(Object responseValue) {
        this.responseValue = responseValue;
    }
}
