package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateBankModel {


    @SerializedName("serviceProviderDetailsId")
    @Expose
    private Integer serviceProviderDetailsId;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("accountHolder")
    @Expose
    private String accountHolder;

    public Integer getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(Integer serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}
