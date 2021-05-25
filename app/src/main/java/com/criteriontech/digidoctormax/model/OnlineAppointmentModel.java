package com.criteriontech.digidoctormax.model;

public class OnlineAppointmentModel {

    int doctorId;
    int isEraUser;
    String patientName;
    int expiredStatus;
    boolean isPrescribed;
    String appointmentIdView;
    String specialty;
    String appointDate;
    String appointTime;
    String problemName;
    String clinicAddress;
    String attachFile;
    String workingHours;
    int isVisit;

    @Override
    public String toString() {
        return "OnlineAppointmentModel{" +
                "doctorId=" + doctorId +
                ", isEraUser=" + isEraUser +
                ", patientName='" + patientName + '\'' +
                ", expiredStatus=" + expiredStatus +
                ", isPrescribed=" + isPrescribed +
                ", appointmentIdView='" + appointmentIdView + '\'' +
                ", specialty='" + specialty + '\'' +
                ", appointDate='" + appointDate + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", problemName='" + problemName + '\'' +
                ", clinicAddress='" + clinicAddress + '\'' +
                ", attachFile='" + attachFile + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", isVisit=" + isVisit +
                '}';
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(int isEraUser) {
        this.isEraUser = isEraUser;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getExpiredStatus() {
        return expiredStatus;
    }

    public void setExpiredStatus(int expiredStatus) {
        this.expiredStatus = expiredStatus;
    }

    public boolean isPrescribed() {
        return isPrescribed;
    }

    public void setPrescribed(boolean prescribed) {
        isPrescribed = prescribed;
    }

    public String getAppointmentIdView() {
        return appointmentIdView;
    }

    public void setAppointmentIdView(String appointmentIdView) {
        this.appointmentIdView = appointmentIdView;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public int getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(int isVisit) {
        this.isVisit = isVisit;
    }
}
