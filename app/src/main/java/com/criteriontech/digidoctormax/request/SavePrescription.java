package com.criteriontech.digidoctormax.request;

public class SavePrescription {
    private Integer appointmentID;
    private Integer serviceProviderDetailsID;
    private String diagnosis;
    private String dtDataTableMedicine;
    private String userMobileNo;
    private String testID;
    private String recommendedDiet;
    private String foodToBeAvoided;
    private String otherAdvise;
    private String otherDiet;
    private String symptomID;

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Integer getServiceProviderDetailsID() {
        return serviceProviderDetailsID;
    }

    public void setServiceProviderDetailsID(Integer serviceProviderDetailsID) {
        this.serviceProviderDetailsID = serviceProviderDetailsID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDtDataTableMedicine() {
        return dtDataTableMedicine;
    }

    public void setDtDataTableMedicine(String dtDataTableMedicine) {
        this.dtDataTableMedicine = dtDataTableMedicine;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getRecommendedDiet() {
        return recommendedDiet;
    }

    public void setRecommendedDiet(String recommendedDiet) {
        this.recommendedDiet = recommendedDiet;
    }

    public String getFoodToBeAvoided() {
        return foodToBeAvoided;
    }

    public void setFoodToBeAvoided(String foodToBeAvoided) {
        this.foodToBeAvoided = foodToBeAvoided;
    }

    public String getOtherAdvise() {
        return otherAdvise;
    }

    public void setOtherAdvise(String otherAdvise) {
        this.otherAdvise = otherAdvise;
    }

    public String getOtherDiet() {
        return otherDiet;
    }

    public void setOtherDiet(String otherDiet) {
        this.otherDiet = otherDiet;
    }

    public String getSymptomID() {
        return symptomID;
    }

    public void setSymptomID(String symptomID) {
        this.symptomID = symptomID;
    }
}
