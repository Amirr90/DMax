
package com.criteriontech.digidoctormax.model;

public class WritePrescriptionForQuarantineModel {
    int serviceProviderDetailsId;
    int isEraUser;
    int testId;
    int memberId;
    String diagnosis;
    String dtDataTableMedicine;
    String foodToBeAvoided;
    String otherAdvise;
    String otherDiet;
    String recommendedDiet;
    int homeIsolationId;

    public int getHomeIsolationId() {
        return homeIsolationId;
    }

    public void setHomeIsolationId(int homeIsolationId) {
        this.homeIsolationId = homeIsolationId;
    }

    public int getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(int serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public int getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(int isEraUser) {
        this.isEraUser = isEraUser;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public String getRecommendedDiet() {
        return recommendedDiet;
    }

    public void setRecommendedDiet(String recommendedDiet) {
        this.recommendedDiet = recommendedDiet;
    }


}

