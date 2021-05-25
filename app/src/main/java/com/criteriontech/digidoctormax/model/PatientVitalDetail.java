package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientVitalDetail {

    @SerializedName("vitalId")
    @Expose
    private Integer vitalId;
    @SerializedName("vitalName")
    @Expose
    private String vitalName;
    @SerializedName("vitalValue")
    @Expose
    private String vitalValue;



    @SerializedName("vitalTime")
    @Expose
    private String vitalTime;

    public Integer getVitalId() {
        return vitalId;
    }

    public void setVitalId(Integer vitalId) {
        this.vitalId = vitalId;
    }

    public String getVitalName() {
        return vitalName;
    }

    public void setVitalName(String vitalName) {
        this.vitalName = vitalName;
    }

    public String getVitalValue() {
        return vitalValue;
    }

    public void setVitalValue(String vitalValue) {
        this.vitalValue = vitalValue;
    }

    public String getVitalTime() {
        return vitalTime;
    }

    public void setVitalTime(String vitalTime) {
        this.vitalTime = vitalTime;
    }

    @Override
    public String toString() {
        return "PatientVitalDetail{" +
                "vitalId=" + vitalId +
                ", vitalName='" + vitalName + '\'' +
                ", vitalValue='" + vitalValue + '\'' +
                ", vitalTime='" + vitalTime + '\'' +
                '}';
    }


}