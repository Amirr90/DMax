package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpValue {
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }
}
