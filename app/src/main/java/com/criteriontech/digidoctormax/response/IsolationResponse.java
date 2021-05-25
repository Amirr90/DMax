package com.criteriontech.digidoctormax.response;

import com.criteriontech.digidoctormax.model.HomeIsolationReqModel;

import java.util.ArrayList;
import java.util.List;

public class IsolationResponse {

    Integer responseCode;
    String responseMessage;
    List<HomeIsolationReqModel> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<HomeIsolationReqModel> getResponseValue() {
        return responseValue;
    }
}
