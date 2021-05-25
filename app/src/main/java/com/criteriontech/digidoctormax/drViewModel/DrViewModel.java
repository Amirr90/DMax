package com.criteriontech.digidoctormax.drViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.criteriontech.digidoctormax.model.ChatModel;
import com.criteriontech.digidoctormax.model.ClinicNumOfDocValue;
import com.criteriontech.digidoctormax.model.CollectedFeeValue;
import com.criteriontech.digidoctormax.model.NoOfPatientValue;
import com.criteriontech.digidoctormax.model.VisitedPatientClinicValue;
import com.criteriontech.digidoctormax.repository.DoctorRepo;
import com.criteriontech.digidoctormax.request.ClinicDashboardDetails;
import com.criteriontech.digidoctormax.request.GenerateOTP;
import com.criteriontech.digidoctormax.response.OtpResp;

import java.util.List;

public class DrViewModel extends ViewModel {

    DoctorRepo repo = new DoctorRepo();

    public LiveData<OtpResp> getOtp(GenerateOTP generateOTP, Activity activity) {
        return repo.loadOtpData(generateOTP, activity);
    }

    public LiveData<List<ClinicNumOfDocValue>> clinicDashboardNumOfDoctors(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        return repo.clinicDashboardNumOfDoctors(clinicDashboardDetails, activity);
    }

    public LiveData<List<CollectedFeeValue>> clinicDashboardFeeCollected(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        return repo.clinicDashboardFeeCollected(clinicDashboardDetails, activity);
    }

    public LiveData<List<VisitedPatientClinicValue>> clinicDashboardVisitedPatient(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        return repo.clinicDashboardVisitedPatient(clinicDashboardDetails, activity);
    }

    public LiveData<List<NoOfPatientValue>> drDashboardNumOfPatients(ClinicDashboardDetails clinicDashboardDetails, Activity activity) {
        return repo.drDashboardNumOfPatients(clinicDashboardDetails, activity);
    }

    public LiveData<List<ChatModel>> getChatData(NoOfPatientValue appointmentModel) {

        return repo.getChatData(appointmentModel);
    }
}
