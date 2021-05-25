package com.criteriontech.digidoctormax.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

import static com.criteriontech.digidoctormax.utils.AppUtils.parseDate;

public class GetPatientMedicationMainModel {

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("problemName")
    @Expose
    private String problemName;
    @SerializedName("drName")
    @Expose
    private String drName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("speciality")
    @Expose
    private String specialty;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("profilePhotoPath")
    @Expose
    private String profilePhotoPath;
    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("medicineDetails")
    @Expose
    private List<GetPatientMedicationMedicineModel> medicineDetails = null;
    @SerializedName("adviseDetails")
    @Expose
    private List<GetPatientMedicationAdviceModel> adviseDetails = null;


    String age;
    String gender;
    String address;
    String memberId;

    public String getMemberId() {
        return memberId;
    }

    public String getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public List<GetPatientMedicationAdviceModel> getAdviseDetails() {
        return adviseDetails;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public String getTestName() {
        return testName;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getDrName() {
        return drName;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getStartDate() {
        return parseDate(startDate,"dd MMMM yyyy");
    }

    public String getSpecialty() {
        if (null == specialty || specialty.isEmpty())
            return "Not defined";
        return specialty;
    }

    public String getLocation() {
        return location;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<GetPatientMedicationMedicineModel> getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(List<GetPatientMedicationMedicineModel> medicineDetails) {
        this.medicineDetails = medicineDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetPatientMedicationMainModel)) return false;
        GetPatientMedicationMainModel mainModel = (GetPatientMedicationMainModel) o;
        return Objects.equals(getName(), mainModel.getName()) &&
                getProblemName().equals(mainModel.getProblemName()) &&
                Objects.equals(getDrName(), mainModel.getDrName()) &&
                Objects.equals(getStartDate(), mainModel.getStartDate()) &&
                Objects.equals(getSpecialty(), mainModel.getSpecialty()) &&
                Objects.equals(getLocation(), mainModel.getLocation()) &&
                Objects.equals(getTestName(), mainModel.getTestName()) &&
                Objects.equals(getFilePath(), mainModel.getFilePath()) &&
                Objects.equals(getProfilePhotoPath(), mainModel.getProfilePhotoPath()) &&
                Objects.equals(getAppointmentId(), mainModel.getAppointmentId()) &&
                Objects.equals(getType(), mainModel.getType()) &&
                Objects.equals(getMedicineDetails(), mainModel.getMedicineDetails()) &&
                Objects.equals(getAdviseDetails(), mainModel.getAdviseDetails()) &&
                Objects.equals(getAge(), mainModel.getAge()) &&
                Objects.equals(getGender(), mainModel.getGender()) &&
                Objects.equals(getAddress(), mainModel.getAddress()) &&
                Objects.equals(getMemberId(), mainModel.getMemberId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getProblemName(), getDrName(), getStartDate(), getSpecialty(), getLocation(), getTestName(), getFilePath(), getProfilePhotoPath(), getAppointmentId(), getType(), getMedicineDetails(), getAdviseDetails(), getAge(), getGender(), getAddress(), getMemberId());
    }


    public static DiffUtil.ItemCallback<GetPatientMedicationMainModel> itemCallback = new DiffUtil.ItemCallback<GetPatientMedicationMainModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull GetPatientMedicationMainModel oldItem, @NonNull GetPatientMedicationMainModel newItem) {
            return oldItem.getProblemName().equalsIgnoreCase(newItem.getProblemName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull GetPatientMedicationMainModel oldItem, @NonNull GetPatientMedicationMainModel newItem) {
            return oldItem.equals(newItem);
        }
    };


    @Override
    public String toString() {
        return "GetPatientMedicationMainModel{" +
                "name='" + name + '\'' +
                ", problemName='" + problemName + '\'' +
                ", drName='" + drName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", specialty='" + specialty + '\'' +
                ", location='" + location + '\'' +
                ", testName='" + testName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", appointmentId=" + appointmentId +
                ", type=" + type +
                ", medicineDetails=" + medicineDetails +
                ", adviseDetails=" + adviseDetails +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
