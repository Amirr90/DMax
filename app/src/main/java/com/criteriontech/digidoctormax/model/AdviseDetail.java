package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdviseDetail {
    @SerializedName("appointmentId")
    @Expose
    public String appointmentId;
    @SerializedName("recommendedDiet")
    @Expose
    public String recommendedDiet;
    @SerializedName("avoidedDiet")
    @Expose
    public String avoidedDiet;
    @SerializedName("otherDiet")
    @Expose
    public String otherDiet;
    @SerializedName("advice")
    @Expose
    public String advice;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getRecommendedDiet() {
        return recommendedDiet;
    }

    public void setRecommendedDiet(String recommendedDiet) {
        this.recommendedDiet = recommendedDiet;
    }

    public String getAvoidedDiet() {
        return avoidedDiet;
    }

    public void setAvoidedDiet(String avoidedDiet) {
        this.avoidedDiet = avoidedDiet;
    }

    public String getOtherDiet() {
        return otherDiet;
    }

    public void setOtherDiet(String otherDiet) {
        this.otherDiet = otherDiet;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
