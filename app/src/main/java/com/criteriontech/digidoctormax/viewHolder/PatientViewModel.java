package com.criteriontech.digidoctormax.viewHolder;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.criteriontech.digidoctormax.model.GetPatientMedicationMainModel;
import com.criteriontech.digidoctormax.model.InvestigationModel;
import com.criteriontech.digidoctormax.model.User;
import com.criteriontech.digidoctormax.model.VitalResponse;
import com.criteriontech.digidoctormax.repository.DoctorRepo;
import com.criteriontech.digidoctormax.request.VitalModel;

import java.util.List;

public class PatientViewModel extends ViewModel {


    DoctorRepo repo = new DoctorRepo();


    public LiveData<List<GetPatientMedicationMainModel>> getPrescriptionData(String memberId, Activity activity) {
        return repo.getPrescriptionData(memberId, activity);
    }

    public LiveData<List<InvestigationModel>> getInvestigationData(User user) {
        return repo.getInvestigationData(user);
    }


    public LiveData<List<VitalResponse.VitalDateVise>> getVitals(VitalModel vitalModel, Activity activity) {
        return repo.getVitals(vitalModel, activity);
    }

}
