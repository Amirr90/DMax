package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuarantinePatientListRes {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseValue")
    @Expose
    private List<QuarantineModel> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<QuarantineModel> getResponseValue() {
        return responseValue;
    }
}
