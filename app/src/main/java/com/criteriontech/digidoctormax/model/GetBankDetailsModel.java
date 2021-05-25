package com.criteriontech.digidoctormax.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBankDetailsModel {
    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("requestTime")
    @Expose
    private String requestTime;
    @SerializedName("approveDate")
    @Expose
    private Object approveDate;
    @SerializedName("isApproved")
    @Expose
    private Integer isApproved;

    @Override
    public String toString() {
        return "GetBankDetailsModel{" +
                "id=" + id +
                ", serviceProviderDetailsId=" + serviceProviderDetailsId +
                ", accountNo='" + accountNo + '\'' +
                ", ifsc='" + ifsc + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountHolder='" + accountHolder + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", approveDate=" + approveDate +
                ", isApproved=" + isApproved +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Object getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Object approveDate) {
        this.approveDate = approveDate;
    }

    public Integer getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Integer isApproved) {
        this.isApproved = isApproved;
    }

}
