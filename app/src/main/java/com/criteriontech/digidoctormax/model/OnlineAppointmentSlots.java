package com.criteriontech.digidoctormax.model;

public class OnlineAppointmentSlots {

    private String serviceProviderDetailsId;
    private String appointDate;
    private String isEraUser;


    public String getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(String serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(String isEraUser) {
        this.isEraUser = isEraUser;
    }

    @Override
    public String toString() {
        return "OnlineAppointmentSlots{" +
                "serviceProviderDetailsId='" + serviceProviderDetailsId + '\'' +
                ", appointDate='" + appointDate + '\'' +
                ", isEraUser='" + isEraUser + '\'' +
                '}';
    }
}
