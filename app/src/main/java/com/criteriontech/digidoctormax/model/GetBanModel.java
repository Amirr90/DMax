package com.criteriontech.digidoctormax.model;

public class GetBanModel {

    private int serviceProviderDetailsId;

    public void setServiceProviderDetailsId(int serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    @Override
    public String toString() {
        return "GetBanModel{" +
                "serviceProviderDetailsId=" + serviceProviderDetailsId +
                '}';
    }

    public int getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }


}
