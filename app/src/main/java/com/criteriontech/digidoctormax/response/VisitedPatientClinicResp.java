package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.VisitedPatientClinicValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VisitedPatientClinicResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<VisitedPatientClinicValue> responseValue = null;

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

    public List<VisitedPatientClinicValue> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<VisitedPatientClinicValue> responseValue) {
        this.responseValue = responseValue;
    }
}
