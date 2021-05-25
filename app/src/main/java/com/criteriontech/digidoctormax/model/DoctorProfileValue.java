package com.criteriontech.digidoctormax.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.criteriontech.digidoctormax.utils.AppUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfileValue extends BaseObservable {
    String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @SerializedName("serviceProviderDetailsId")
    @Expose
    public Integer serviceProviderDetailsId;
    @SerializedName("serviceProviderLoginId")
    @Expose
    public Integer serviceProviderLoginId;
    @SerializedName("serviceProviderTypeId")
    @Expose
    public Integer serviceProviderTypeId;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("serviceProviderType")
    @Expose
    public String serviceProviderType;
    @SerializedName("specialityName")
    @Expose
    public String specialityName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("emailId")
    @Expose
    public String emailId;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("pinCode")
    @Expose
    public String pinCode;
    @SerializedName("cityId")
    @Expose
    public Integer cityId;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("stateId")
    @Expose
    public Integer stateId;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("countryId")
    @Expose
    public Integer countryId;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("profilePhotoPath")
    @Expose
    public String profilePhotoPath;
    @SerializedName("userMobileNo")
    @Expose
    public String userMobileNo;
    @SerializedName("workDescription")
    @Expose
    public String workDescription;
    @SerializedName("registrationNo")
    @Expose
    public String registrationNo;
    @SerializedName("registrationCouncil")
    @Expose
    public String registrationCouncil;
    @SerializedName("registrationYear")
    @Expose
    public String registrationYear;
    @SerializedName("degree")
    @Expose
    public String degree;
    @SerializedName("college")
    @Expose
    public String college;
    @SerializedName("yearOfCompletion")
    @Expose
    public String yearOfCompletion;
    @SerializedName("yearOfExperience")
    @Expose
    public String yearOfExperience;
    @SerializedName("ownOrVisitEstabilshment")
    @Expose
    public Boolean ownOrVisitEstabilshment;
    @SerializedName("establishmentName")
    @Expose
    public String establishmentName;
    @SerializedName("establishmentCityId")
    @Expose
    public Integer establishmentCityId;
    @SerializedName("establishmentCity")
    @Expose
    public String establishmentCity;
    @SerializedName("establishmentLocality")
    @Expose
    public String establishmentLocality;

    @SerializedName("drFee")
    @Expose
    public String drFee;

    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("currencyId")
    @Expose
    public Integer currencyId;
    @SerializedName("symbol")
    @Expose
    public String symbol;
    @SerializedName("primaryStatus")
    @Expose
    public Integer primaryStatus;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("signature")
    @Expose
    public String signature;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("specialityId")
    @Expose
    public Integer specialityId;
    @SerializedName("timeDetails")
    @Expose
    public String timeDetails;
    @SerializedName("departmentId")
    @Expose
    public Integer departmentId;
    @SerializedName("speciality")
    @Expose
    public String speciality;
    @SerializedName("timeSlots")
    @Expose
    public String timeSlots;
    @SerializedName("clinicName")
    @Expose
    public String clinicName;
    private String dtDataTable;
    private int duration = 10;
    private String followUpDuration;
    private String reVisitCount;

    @Override
    public String toString() {
        return "DoctorProfileValue{" +
                "mobileNo='" + mobileNo + '\'' +
                ", serviceProviderDetailsId=" + serviceProviderDetailsId +
                ", serviceProviderLoginId=" + serviceProviderLoginId +
                ", serviceProviderTypeId=" + serviceProviderTypeId +
                ", id=" + id +
                ", serviceProviderType='" + serviceProviderType + '\'' +
                ", specialityName='" + specialityName + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", emailId='" + emailId + '\'' +
                ", address='" + address + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", cityId=" + cityId +
                ", city='" + city + '\'' +
                ", stateId=" + stateId +
                ", state='" + state + '\'' +
                ", countryId=" + countryId +
                ", country='" + country + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", userMobileNo='" + userMobileNo + '\'' +
                ", workDescription='" + workDescription + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", registrationCouncil='" + registrationCouncil + '\'' +
                ", registrationYear='" + registrationYear + '\'' +
                ", degree='" + degree + '\'' +
                ", college='" + college + '\'' +
                ", yearOfCompletion='" + yearOfCompletion + '\'' +
                ", yearOfExperience='" + yearOfExperience + '\'' +
                ", ownOrVisitEstabilshment=" + ownOrVisitEstabilshment +
                ", establishmentName='" + establishmentName + '\'' +
                ", establishmentCityId=" + establishmentCityId +
                ", establishmentCity='" + establishmentCity + '\'' +
                ", establishmentLocality='" + establishmentLocality + '\'' +
                ", drFee='" + drFee + '\'' +
                ", currency='" + currency + '\'' +
                ", currencyId=" + currencyId +
                ", symbol='" + symbol + '\'' +
                ", primaryStatus=" + primaryStatus +
                ", dob='" + dob + '\'' +
                ", signature='" + signature + '\'' +
                ", age='" + age + '\'' +
                ", specialityId=" + specialityId +
                ", timeDetails='" + timeDetails + '\'' +
                ", departmentId=" + departmentId +
                ", speciality='" + speciality + '\'' +
                ", timeSlots='" + timeSlots + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", dtDataTable='" + dtDataTable + '\'' +
                ", duration=" + duration +
                ", followUpDuration='" + followUpDuration + '\'' +
                ", revisitCount='" + reVisitCount + '\'' +
                '}';
    }

    public String getFollowUpDuration() {
        return followUpDuration;
    }

    public void setFollowUpDuration(String followUpDuration) {
        this.followUpDuration = followUpDuration;
    }

    public String getReVisitCount() {
        return reVisitCount;
    }

    public void setReVisitCount(String reVisitCount) {
        this.reVisitCount = reVisitCount;
    }

    @Bindable
    public Integer getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(Integer serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    @Bindable
    public Integer getServiceProviderLoginId() {
        return serviceProviderLoginId;
    }

    public void setServiceProviderLoginId(Integer serviceProviderLoginId) {
        this.serviceProviderLoginId = serviceProviderLoginId;
    }

    @Bindable
    public Integer getServiceProviderTypeId() {
        return serviceProviderTypeId;
    }

    public void setServiceProviderTypeId(Integer serviceProviderTypeId) {
        this.serviceProviderTypeId = serviceProviderTypeId;
    }

    @Bindable
    public String getServiceProviderType() {
        return serviceProviderType;
    }

    public void setServiceProviderType(String serviceProviderType) {
        this.serviceProviderType = serviceProviderType;
    }

    @Bindable
    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Bindable
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Bindable
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Bindable
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Bindable
    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    @Bindable
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Bindable
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Bindable
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Bindable
    public String getProfilePhotoPath() {
        if (null == profilePhotoPath)
            return "";
        else
            return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    @Bindable
    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    @Bindable
    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    @Bindable
    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo.trim();
    }

    @Bindable
    public String getRegistrationCouncil() {
        return registrationCouncil;
    }

    public void setRegistrationCouncil(String registrationCouncil) {
        this.registrationCouncil = registrationCouncil;
    }

    @Bindable
    public String getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(String registrationYear) {
        this.registrationYear = registrationYear;
    }

    @Bindable
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree.trim();
    }

    @Bindable
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Bindable
    public String getYearOfCompletion() {
        return yearOfCompletion;
    }

    public void setYearOfCompletion(String yearOfCompletion) {
        this.yearOfCompletion = yearOfCompletion;
    }

    @Bindable
    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    @Bindable
    public Boolean getOwnOrVisitEstabilshment() {
        return ownOrVisitEstabilshment;
    }

    public void setOwnOrVisitEstabilshment(Boolean ownOrVisitEstabilshment) {
        this.ownOrVisitEstabilshment = ownOrVisitEstabilshment;
    }

    @Bindable
    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    @Bindable
    public Integer getEstablishmentCityId() {
        return establishmentCityId;
    }

    public void setEstablishmentCityId(Integer establishmentCityId) {
        this.establishmentCityId = establishmentCityId;
    }

    @Bindable
    public String getEstablishmentCity() {
        return establishmentCity;
    }

    public void setEstablishmentCity(String establishmentCity) {
        this.establishmentCity = establishmentCity;
    }

    @Bindable
    public String getEstablishmentLocality() {
        return establishmentLocality;
    }

    public void setEstablishmentLocality(String establishmentLocality) {
        this.establishmentLocality = establishmentLocality;
    }

    @Bindable
    public String getDrFee() {
        if (drFee == null)
            return "0";
        else return drFee;
    }

    public void setDrFee(String drFee) {
        this.drFee = drFee;
    }

    @Bindable
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Bindable
    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    @Bindable
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Bindable
    public Integer getPrimaryStatus() {
        return primaryStatus;
    }

    public void setPrimaryStatus(Integer primaryStatus) {
        this.primaryStatus = primaryStatus;
    }

    @Bindable
    public String getDob() {
        try {
            return AppUtils.formatInputDate(dob);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setDob(String dob) {
        this.dob = AppUtils.formatOutputDate(dob);
    }

    public void setDob(String dob, String empty) {
        this.dob = dob;
    }

    @Bindable
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Bindable
    public Object getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    @Bindable
    public String getTimeDetails() {
        return timeDetails;
    }

    public void setTimeDetails(String timeDetails) {
        this.timeDetails = timeDetails;
    }

    @Bindable
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Bindable
    public String getSpeciality() {
        return speciality + ", " + age;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(String timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
