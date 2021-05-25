package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.NoOfPatientValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoOfPatientResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<NoOfPatientValue> responseValue = null;

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

    public List<NoOfPatientValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<NoOfPatientValue> responseValue) {
        this.responseValue = responseValue;
    }
}
