package com.criteriontech.digidoctormax.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.criteriontech.digidoctormax.adapters.MedicineDetailsAdapter;
import com.criteriontech.digidoctormax.adapters.VitalDetailsAdapter;
import com.criteriontech.digidoctormax.databinding.ActivityPatientDetailsBinding;
import com.criteriontech.digidoctormax.model.AdviseDetail;
import com.criteriontech.digidoctormax.model.PatientMedicineDetail;
import com.criteriontech.digidoctormax.model.PatientVitalDetail;
import com.criteriontech.digidoctormax.request.PatientMedicationDetails;
import com.criteriontech.digidoctormax.response.PatientMedicationResp;
import com.criteriontech.digidoctormax.utils.ApiCallbackInterface;
import com.criteriontech.digidoctormax.utils.ApiUtils;
import com.criteriontech.digidoctormax.utils.AppUtils;
import com.criteriontech.digidoctormax.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class PatientDetailsActivity extends Fragment {
    private static final String TAG = "PatientDetailsActivity";
    ActivityPatientDetailsBinding binding;
    NavController navController;
    PatientMedicationDetails patientMedicationDetails;
    MedicineDetailsAdapter medicineDetailsAdapter;
    PatientMedicineDetail patientMedicineDetail;
    List<PatientMedicineDetail> medicineDetailList;
    PatientVitalDetail patientVitalDetail;
    List<PatientVitalDetail> vitalDetailss;
    VitalDetailsAdapter vitalDetailsAdapter;
    List<AdviseDetail> advise;
    AdviseDetail adviseDetail;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityPatientDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppUtils.showRequestDialog(requireActivity());


        medicineDetailList = new ArrayList<>();
        vitalDetailss = new ArrayList<>();
        advise = new ArrayList<>();

        navController = Navigation.findNavController(view);
        patientMedicineDetail = new PatientMedicineDetail();
        patientVitalDetail = new PatientVitalDetail();
        adviseDetail = new AdviseDetail();
        medicineDetailsAdapter = new MedicineDetailsAdapter(medicineDetailList);
        vitalDetailsAdapter = new VitalDetailsAdapter(vitalDetailss, requireActivity());
        binding.rvVital.setAdapter(vitalDetailsAdapter);
        binding.rvPrescriptionDt.setAdapter(medicineDetailsAdapter);
        patientMedicationDetails = new PatientMedicationDetails();
        patientMedicationDetails.setAppointmentID(getArguments().getInt("appointmentId"));
        patientMedicationDetails.setUserMobileNo(SharedPrefManager.getInstance(requireActivity()).getUser().getMobileNo());
        ApiUtils.getPatientMedicationDetails(patientMedicationDetails, new ApiCallbackInterface() {
            @Override
            public void onSuccess(Object obj) {
                if (!((PatientMedicationResp) obj).getResponseValue().isEmpty()) {
                    binding.setPatient(((PatientMedicationResp) obj).getResponseValue().get(0));
                    PatientMedicationResp resp = (PatientMedicationResp) obj;

                    medicineDetailList.clear();
                    medicineDetailList.addAll(resp.getResponseValue().get(0).getMedicineDetails());
                    Log.v("sfgfdgd", String.valueOf(medicineDetailList.size()));
                    medicineDetailsAdapter.notifyDataSetChanged();
                    if (medicineDetailList.isEmpty()) {
                        binding.clPrescription.setVisibility(View.GONE);
                        binding.textView37.setVisibility(View.GONE);
                    } else {
                        binding.clPrescription.setVisibility(View.VISIBLE);
                        binding.textView37.setVisibility(View.VISIBLE);
                    }
                    if (!resp.getResponseValue().get(0).getAdviseDetails().isEmpty()) {
                        advise.addAll(resp.getResponseValue().get(0).getAdviseDetails());
                        binding.setAdvise(advise.get(0));
                        binding.tvVitall.setVisibility(View.VISIBLE);
                    } else binding.tvVitall.setVisibility(View.GONE);

                    JSONArray jsonArray;
                    try {
                        Log.v("fsdfsd", resp.getResponseValue().get(0).getVitalDetails());
                        jsonArray = new JSONArray(resp.getResponseValue().get(0).getVitalDetails());

                        for (int a = 0; a < jsonArray.length(); a++) {
                            PatientVitalDetail patientVitalDetail = new PatientVitalDetail();
                            String VitalValue = jsonArray.getJSONObject(a).getString("vitalName");
                            Log.d("", "VitalVAlue" + VitalValue);
                            patientVitalDetail.setVitalId(Integer.parseInt(jsonArray.getJSONObject(a).getString("vitalId")));
                            patientVitalDetail.setVitalName(jsonArray.getJSONObject(a).getString("vitalName"));
                            patientVitalDetail.setVitalTime(jsonArray.getJSONObject(a).getString("vitalTime"));
                            patientVitalDetail.setVitalValue(jsonArray.getJSONObject(a).getString("vitalValue"));
                            vitalDetailss.add(patientVitalDetail);
                        }
                        if (resp.getResponseValue().get(0).getVitalDetails().equalsIgnoreCase("")) {
                            binding.clVital.setVisibility(View.GONE);
                            binding.tvVital.setVisibility(View.GONE);
                        } else {
                            binding.clVital.setVisibility(View.VISIBLE);
                            binding.tvVital.setVisibility(View.VISIBLE);
                        }
                        vitalDetailsAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        binding.clVital.setVisibility(View.GONE);
                        binding.tvVital.setVisibility(View.GONE);
                    }


                } else {
                    Toast.makeText(requireActivity(), "Something went wrong ! Try Again", Toast.LENGTH_SHORT).show();
                }


                AppUtils.hideDialog();

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                AppUtils.hideDialog();
            }
        });
        //show Past MedicalHistory Page
        // binding.textView28.setOnClickListener(view12 -> ShowBottomDialog());
    }
/*
    private void ShowBottomDialog() {
        BottomDialog dialog = new BottomDialog(requireActivity());
        dialog.title("Past Medical History");
        dialog.canceledOnTouchOutside(true);
        dialog.cancelable(true);
        dialog.inflateMenu(R.menu.menu_bottom_dialog);
        dialog.setOnItemSelectedListener(id -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", getArguments().getInt("memberId"));

            Log.d(TAG, "ShowBottomDialog: " + getArguments().getInt("memberId"));
            switch (id) {
                case R.id.investigation:
                    navController.navigate(R.id.action_patientDetailsActivity_to_investigationsFragment, bundle);
                    return true;
                case R.id.prescription:
                    navController.navigate(R.id.action_patientDetailsActivity_to_prescriptionsFragment, bundle);
                    return true;
                case R.id.vital:
                    navController.navigate(R.id.action_patientDetailsActivity_to_vitalsFragment, bundle);
                    return true;
                default:
                    return false;
            }
        });
        dialog.show();
    }*/
}

