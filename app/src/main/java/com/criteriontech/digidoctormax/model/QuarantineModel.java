package com.criteriontech.digidoctormax.model;

public class QuarantineModel {

    String profilePhotoPath;
    String name;
    String appointmentDate;
    String appointmentTime;


    public int getMemberId() {
        return memberId;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getComoribid() {
        return comoribid;
    }

    public String getStymptoms() {
        return stymptoms;
    }

    public String getTestDate() {
        return testDate;
    }

    public String getIsCovidTest() {
        return isCovidTest;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getAddress() {
        return address;
    }

    int memberId;
    int packageId;
    String comoribid;
    String stymptoms;
    String testDate;
    String isCovidTest;
    String packageName;
    String mobileNo;
    String address;
    String allergires;
    String age;
    String gender;
    int isPrescribed;

    public int getIsPrescribed() {
        return isPrescribed;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getHomeIsolationId() {
        return homeIsolationId;
    }

    int appointmentId;
    int homeIsolationId;


    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAllergires() {
        return allergires;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public String getName() {
        return name;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }
}
