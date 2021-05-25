package com.criteriontech.digidoctormax.model;

import java.util.List;

public class ResponseModel {

    String responseMessage;
    Integer responseCode;
    List<DrugIntrcationModel> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<DrugIntrcationModel> getResponseValue() {
        return responseValue;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
