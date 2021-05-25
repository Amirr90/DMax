package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPatientMedicationAdviceModel {
    @SerializedName("recommendedDiet")
    @Expose
    private String recommendedDiet;
    @SerializedName("avoidedDiet")
    @Expose
    private String avoidedDiet;
    @SerializedName("otherDiet")
    @Expose
    private String otherDiet;
    @SerializedName("advice")
    @Expose
    private String advice;

    public String getRecommendedDiet() {
        return recommendedDiet;
    }

    public String getAvoidedDiet() {
        return avoidedDiet;
    }

    public String getOtherDiet() {
        return otherDiet;
    }

    public String getAdvice() {
        return advice;
    }
}
